package org.ray.flamingo.pollution;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataInit {
	private static final String POLLUTION = "pollution";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private RedisTemplate<String, Umbrella> template;
	
	private HashOperations<String, Character, Umbrella> hash;
	
	@PostConstruct
	private void setUp() {
		hash = template.opsForHash();
	}
	
	@EventListener(ContextRefreshedEvent.class)
	public void loadSenstiveInfomationWhenApplicationStart() {
		@SuppressWarnings("unchecked")
		List<String> bads = (List<String>)em.createNativeQuery("select * from Sensitive_Word").getResultList();
		
		for(String bad : bads) {
			Umbrella umbrella = hash.get(POLLUTION, bad.charAt(0));
			
			if( umbrella == null)
				umbrella = new Umbrella();
			
			hash.put(POLLUTION, bad.charAt(0), umbrella.enBlacklist(bad));
		}
		
		log.info(hash.entries(POLLUTION).toString());
	}

}
