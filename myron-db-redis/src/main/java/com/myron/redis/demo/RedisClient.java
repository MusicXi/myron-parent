package com.myron.redis.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient {
	private Jedis jedis;//非切片的客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片的客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池

    public RedisClient() 
    { 
        this.initialPool(); 
        this.initialShardedPool(); 
        shardedJedis = shardedJedisPool.getResource(); 
        jedis = jedisPool.getResource();  
        //查看服务是否运行
        System.out.println("Server is running: "+jedis.ping());
    }
    
    /**
     * 初始化非切片池
     */
    private void initialPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxIdle(20); 
        config.setMaxIdle(5); 
        config.setMaxWaitMillis(1000l); 
        config.setTestOnBorrow(false); 
        
        jedisPool = new JedisPool(config,"127.0.0.1",6379);
    }
    
    /** 
     * 初始化切片池 
     */ 
    private void initialShardedPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxIdle(20); 
        config.setMaxIdle(5); 
        config.setMaxWaitMillis(1000l); 
        config.setTestOnBorrow(false); 
        // slave链接 
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
        JedisShardInfo info=new JedisShardInfo("127.0.0.1", 6379, "master");
       // info.setPassword("demo123");
        shards.add(info); 

        // 构造池 
        shardedJedisPool = new ShardedJedisPool(config, shards); 
      
    } 
    
    public void show() {     
        KeyOperate(); 
        StringOperate(); 
        ListOperate(); 
        SetOperate();
        SortedSetOperate();
        HashOperate(); 
       /* this.jedis=this.jedisPool.getResource();
        this.shardedJedis=shardedJedisPool.getResource();
        
        System.out.println(this.jedis.get("name"));*/
        
        // 释放连接
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    } 

      private void KeyOperate() {
         System.out.println("========key========");
         System.out.println("清空数据库： "+jedis.flushDB());
         // 判断key否存在 
         System.out.println("判断key999键是否存在："+shardedJedis.exists("key999")); 
         System.out.println("新增key001,value001键值对："+shardedJedis.set("key001", "value001")); 
         System.out.println("判断key001是否存在："+shardedJedis.exists("key001"));
         
         // 输出系统中所有的key
         System.out.println("新增key002,value002键值对："+shardedJedis.set("key002", "value002"));
         System.out.println("系统中所有键如下：");
         Set<String> keys = jedis.keys("*"); 
         Iterator<String> it=keys.iterator() ;   
         while(it.hasNext()){   
             String key = it.next();   
             System.out.println(key);   
         }
         
         // 删除某个key,若key不存在，则忽略该命令。
         System.out.println("系统中删除key002: "+jedis.del("key002"));
         System.out.println("判断key002是否存在："+shardedJedis.exists("key002"));
         // 设置 key001的过期时间
         shardedJedis.del("key002");
         System.out.println("设置 key001的过期时间为5秒:"+jedis.expire("key001", 5));
      }

      private void StringOperate() {
    	//TODO
      }

      private void ListOperate() {
    	//TODO
      }

      private void SetOperate() {
    	//TODO
      }

      private void SortedSetOperate() {
    	//TODO
      }
    
      private void HashOperate() {
    	//TODO
      }
      
      public static void main(String[] args){
    	  new RedisClient().show(); 

    	 
      }

}
