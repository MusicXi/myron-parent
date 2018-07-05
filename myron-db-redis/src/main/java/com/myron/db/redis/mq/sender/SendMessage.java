package com.myron.db.redis.mq.sender;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("sendMessage")
public class SendMessage {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("stringRedisTemplate")
	private RedisTemplate redisTemplate;




	public void sendMessage(String channel, Serializable message) {
		redisTemplate.convertAndSend(channel, message);
	}
}
