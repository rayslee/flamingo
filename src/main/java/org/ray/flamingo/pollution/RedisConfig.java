package org.ray.flamingo.pollution;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
	
	@Bean
    public JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory(
						new RedisStandaloneConfiguration("localhost", 6379));
    }
	
	@Bean
	public RedisTemplate<String, Umbrella> redisTemplateForUmbrella() {
        RedisTemplate<String, Umbrella> template = new RedisTemplate<String, Umbrella>();
        
        template.setConnectionFactory(jedisConnectionFactory());
        
        return template;
    }

}
