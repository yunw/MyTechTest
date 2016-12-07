package com.test.example.jedis.lock;

import redis.clients.jedis.Jedis;

/**
 * redis 分布式锁
 * 缺陷： 1、存在单点故障
 *      2、即使加上slave，也会因为异步复制、网络分区，导致可能存在两个客户端都拿到锁
 *      3、如果客户端崩溃，会导致锁在timeout之前不可用，在需要高可用场景下无法忍受
 * @author yinsl
 *
 */
public class DistributedRedisLock {
	
	/**
	 * reids lock key prefix: _DISTRIBUTE_REDIS_LOCK_
	 */
	public static final String key_pre = "_DISTRIBUTE_REDIS_LOCK_";
	
	/**
	 * 默认超时时间:20秒
	 */
	public static final int default_timeout = 20;
	
	public static boolean lock(String key,String value) {
		return lock(key, value, default_timeout);
	}
	
	/**
	 * value的值最好全局唯一，释放锁需要根据该值判断锁的持有人是否是自己
	 * @param key 
	 * @param value
	 * @param timeout
	 * @return
	 */
	public static boolean lock(String key,String value, int timeout) {
		Jedis jedis = new Jedis("192.168.56.75", 6379);
		long result = jedis.setnx(key_pre + key, value);
		if (result == 1) {
			jedis.expire(key, timeout);
		}
		jedis.close();
		return result == 1 ? true : false;
	}
	
	/**
	 * 释放锁。只有在锁存在并且它的值和客户端期望一致的时候（说明锁没有被其它客户端持有）才删除该锁
	 * 这是为了防止客户端的操作时间超出了锁的超时时间，锁被其它客户端持有，如果直接删除则可能出错。
	 * 因此该值必须全局唯一。
	 * @param key
	 * @param value
	 */
	public static void unlock(String key, String value) {
		Jedis jedis = new Jedis("192.168.56.75", 6379);
		String redisValue = jedis.get(key_pre + key);
		if (redisValue != null && redisValue.equals(value)) {
			jedis.del(key_pre+key);
		}
		jedis.close();
	}
	
	

}
