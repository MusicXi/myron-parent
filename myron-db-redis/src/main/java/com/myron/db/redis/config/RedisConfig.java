package com.myron.db.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * spring 4.* 
 * @author Administrator
 *
 */
@Configuration
@ComponentScan(basePackages = "com.myron.db.redis.dao")
//@PropertySource("classpath:redis.properties")
@PropertySources(value = {@PropertySource("classpath:redis.properties")})  
public class RedisConfig {
	
	@Autowired
	private Environment env;
	/**
	 * jedis连接池配置
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		poolConfig.setMaxTotal(600);
		poolConfig.setMaxIdle(200);
		poolConfig.setMaxWaitMillis(1000);
		poolConfig.setTestOnBorrow(true);
		return poolConfig;
	}
	
	/**
	 * jedis连接工厂配置
	 * @param poolConfig
	 * @return
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig poolConfig){
		JedisConnectionFactory connectionFactory=new JedisConnectionFactory();
		connectionFactory.setPoolConfig(poolConfig);
		//connectionFactory.setHostName("10.45.44.35");
		//connectionFactory.setPort(6379);
		connectionFactory.setHostName(env.getProperty("redis.host"));
		connectionFactory.setPort(Integer.parseInt(env.getProperty("redis.port")));
		return connectionFactory;
	}
	
	/**
	 * redis 模板类配置
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory connectionFactory){
		StringRedisTemplate template=new StringRedisTemplate();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		return template;
	}
}
