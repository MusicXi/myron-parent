package com.myron.db.redis.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisDao {
	public <T> Serializable save(T entity);
	
	public <T> void save(String key, T entity);
	
	public <T> T get(String key);
	
	public void delete(String key);
	
	public void delete(List<String> keys);
	
	public Set<String> keys(String pattern);
	
	public Boolean expire(String key, long timeout, TimeUnit unit);

}
