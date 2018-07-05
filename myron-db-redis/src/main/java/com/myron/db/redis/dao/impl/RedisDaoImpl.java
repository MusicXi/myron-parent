package com.myron.db.redis.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.myron.db.redis.dao.RedisDao;




@Repository("redisDao")
public class RedisDaoImpl implements RedisDao{
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("stringRedisTemplate")
	private RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	public <T> Serializable save(T entity){
		
		ValueOperations<String, T> valueops=redisTemplate.opsForValue();
		System.out.println(entity.toString());
		valueops.set(entity.toString(), entity);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> void save(String key, T entity) {
		ValueOperations<String, T> valueops=redisTemplate.opsForValue();
		valueops.set(key, entity);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key){
		ValueOperations<String, T> valueops= redisTemplate.opsForValue();
		return valueops.get(key);
	}
	
	

	@SuppressWarnings("rawtypes")
	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	@SuppressWarnings("rawtypes")
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(String key) {
		this.redisTemplate.delete(key);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(List<String> keys) {
		this.redisTemplate.delete(keys);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> keys(String pattern) {
		return this.redisTemplate.keys(pattern);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean expire(String key, long timeout, TimeUnit unit) {
		return this.redisTemplate.expire(key, timeout, unit);
	}

	
	
	
}
