package org.ray.flamingo.service;

import javax.annotation.PostConstruct;

import org.ray.flamingo.pollution.Umbrella;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PollutionService {
	
	//SET UP
	
	@Autowired
	private RedisTemplate<String, Umbrella> template;
	
	private HashOperations<String, Character, Umbrella> hash;
	
	@PostConstruct
	private void setUp() {
		hash = template.opsForHash();
	}
	
	//PUBLIC METHODS
	
	public boolean isClear(String sentence) {
		return sentence.equals(replaceBadWords(sentence));
	}
	
	public String replaceBadWords(String sentence) {
		return replaceBadWordsWith(sentence, SUBSTITUTE);
	}
	
	//PRIVATE METHODS
	
	private static final char SUBSTITUTE = '*';
	
	private static final String POLLUTION = "pollution";
	
	private String replaceBadWordsWith(String sentence, char with) {
		for(int i = 0; i < sentence.length(); i++) {
			Umbrella umbrella = hash.get(POLLUTION, sentence.charAt(i));
			if(umbrella == null)
				continue;
			
			int matched = umbrella.isBlocked(sentence, i);
			if(matched == -1)
				continue;
			
			String bad = sentence.substring(i, i = ++matched);
			sentence = sentence.replace(bad, bad.replaceAll(".", with + ""));	//String is an immutable class
		}
		
		return sentence;
	}

}
