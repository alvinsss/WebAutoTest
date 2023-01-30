package com.fengjr.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
/**
 * redis链接
 * 
 * @param String Key 
 * @author alvin
 * @version 1.0   
 * @since JDK 1.8
 */


@Test
public class LibRedisClientByKey {

    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池
	Properties dbdataconfig = LibDBDataConfig.getInstance().getProperties();
	public static String Test_redisIp = null;
	public static String Searchkey = null;
	private static final LibLogger logger = LibLogger.getLogger(LibRedisClientByKey.class);
	String values  = null;
	
    public LibRedisClientByKey() 
    { 
        initialPool(); 
        initialShardedPool(); 
        shardedJedis = shardedJedisPool.getResource(); 
        jedis = jedisPool.getResource(); 
    } 
    
   
    /**
     * 初始化非切片池
     */
    private void initialPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(20); 
        config.setMaxIdle(5); 
        config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
        
		if (Test_redisIp == null) {
			Test_redisIp = dbdataconfig.getProperty("redisIp");
			logger.log("Test_redisIp is " +Test_redisIp);
		}
        
        jedisPool = new JedisPool(config,Test_redisIp,6379);
    }
    
    /** 
     * 初始化切片池 
     */ 
    private void initialShardedPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(20); 
        config.setMaxIdle(5); 
        config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
        // slave链接 
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
		if (Test_redisIp == null) {
			Test_redisIp = dbdataconfig.getProperty("redisIp");
			logger.log("Test_redisIp is " +Test_redisIp);
		}
        shards.add(new JedisShardInfo(Test_redisIp, 6379, "master")); 

        // 构造池 
        shardedJedisPool = new ShardedJedisPool(config, shards); 
    } 

    public void show(String Searchkey) {     
//    	StringOperateBykey(Searchkey);
//    	KeyOperateByKey(Searchkey);
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    } 

    
    public void GetKeyValue(){
        logger.log("======================key value=========================="); 
        logger.log("系统中所有键如下：");
        Set<String> keys = jedis.keys("*"); 
        Iterator<String> it=keys.iterator() ;   
        while(it.hasNext()){   
            String key = it.next();   
            logger.log(key);   
        }
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    }
          
      @Test
      public void KeyOperateByKey(String Searchkey) {
  	    { 
  	        logger.log("======================key=========================="); 
  	        
  	        if(shardedJedis.exists(Searchkey)){ 
	           logger.log(Searchkey+"值是"+jedis.get("Searchkey")); 
	           jedisPool.returnResource(jedis);
	           shardedJedisPool.returnResource(shardedJedis);
  	        }else{
 	           logger.log(Searchkey+"值是不存在");   
  	        }

  	        try{ 
  	            Thread.sleep(2000); 
  	        } 
  	        catch (InterruptedException e){ 
  	        	
  	        } 
  	    }
    }

      @Test
      public String StringOperateBykey(String Searchkey) {
    	  
    	  {  
//    	        logger.log("======================StringOperate Bykey=========================="); 
    	        // 清空数据 
//    	        logger.log("清空库中所有数据："+jedis.flushDB());

//    	        logger.log("Searchkey is "+Searchkey);
      	        if(shardedJedis.exists(Searchkey) || Searchkey.isEmpty()){ 
      	           values = shardedJedis.get(Searchkey);
//     	           logger.log(Searchkey+"值是"+values);
     	           jedisPool.returnResource(jedis);
     	           shardedJedisPool.returnResource(shardedJedis);
       	        }else{
      	           logger.log(Searchkey+"值是不存在");   
       	        }
    	        try{ 
    	            Thread.sleep(3000); 
    	        } 
    	        catch (InterruptedException e){ 
    	        	e.printStackTrace();
    	        } 
    	        jedisPool.returnResource(jedis);
    	        shardedJedisPool.returnResource(shardedJedis);
    	    }
    	  return values; 
    	  	
      }

      
      public void ListOperateBykey() {
    	   { 
    	        logger.log("======================list=========================="); 
    	    } 
      }

      @Test
      public String HashOperateBykey(String Searchkey ,String Regionkey) {
    	  { 
//    	        logger.log("======================hash 查询=========================");

//    	        判断Searchkey是否存在
    	        if (shardedJedis.hexists(Searchkey, Regionkey) || Searchkey.isEmpty() ){
//        	        logger.log("获取"+Searchkey+"中所有的域："+shardedJedis.hkeys(Searchkey));
//        	        logger.log("获取"+Searchkey+"对应所有的值："+shardedJedis.hgetAll(Searchkey));
        	        values = shardedJedis.hget(Searchkey,Regionkey);
//        	        logger.log("获取"+Searchkey+"Key对应的"+Regionkey+"值：" +shardedJedis.hget(Searchkey,Regionkey));
    	        }else{
        	        logger.log(Searchkey+"不存在");
    	        }
    	        
    	        jedisPool.returnResource(jedis);
    	        shardedJedisPool.returnResource(shardedJedis);
//    	        logger.log("获取hashs中所有的value："+shardedJedis.hvals("hashs"));
    	        jedisPool.returnResource(jedis);
    	        shardedJedisPool.returnResource(shardedJedis);
    	              
    	    }
    	  return values;
      }
}