package com.myron.redis.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 测试redis服务连接
 * @author Administrator
 *
 */
public class TestDemo {

	public static void main(String[] args) {
		JedisPool pool = new JedisPool(new JedisPoolConfig(),"127.0.0.1", 6379);  
		//连接本地的 Redis 服务
		Jedis jedis = pool.getResource(); 
		//jedis.auth("");
        //查看服务是否运行
        System.out.println("Server is running: "+jedis.ping());
        System.out.println(jedis.get("name"));
        jedis.close();
        pool.close();

	}

}
