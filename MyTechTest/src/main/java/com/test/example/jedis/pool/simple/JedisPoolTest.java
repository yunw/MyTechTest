package com.test.example.jedis.pool.simple;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {

	private static int EXPIRE_SECONDS = 1800;

	private static JedisPool masterPool = getMasterPool();

	private static JedisPool slavePool = getSlavePool();

	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {
			set("testKey" + i, "value" + i);
			System.out.println(get("testKey" + i));
		}

	}

	@SuppressWarnings("deprecation")
    public static void set(String key, String value) {
		Jedis jedis = masterPool.getResource();
		try {
			jedis.setex(key, EXPIRE_SECONDS, value);
		} catch (Exception e) {
			masterPool.returnBrokenResource(jedis);
		} finally {
			masterPool.returnResource(jedis);
		}
	}

	@SuppressWarnings("deprecation")
    public static void hset(String key, String feild, String value) {
		Jedis jedis = masterPool.getResource();
		try {
			jedis.hset(key, feild, value);
		} catch (Exception e) {
			masterPool.returnBrokenResource(jedis);
		} finally {
			masterPool.returnResource(jedis);
		}
	}

	@SuppressWarnings("deprecation")
    public static String hget(String key, String feild) {
		Jedis jedis = slavePool.getResource();
		String result = null;
		try {
			result = jedis.hget(key, feild);
		} catch (Exception e) {
			slavePool.returnBrokenResource(jedis);
		} finally {
			slavePool.returnResource(jedis);
		}
		return result;
	}

	@SuppressWarnings("deprecation")
    public static String get(String key) {
		Jedis jedis = slavePool.getResource();
		String result = null;
		try {
			result = jedis.get(key);
		} catch (Exception e) {
			slavePool.returnBrokenResource(jedis);
		} finally {
			slavePool.returnResource(jedis);
		}
		return result;
	}

	public static JedisPool getMasterPool() {
		JedisPool pool = null;
		if (masterPool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			pool = new JedisPool(config, "10.91.230.232", 6379);
		}
		return pool;
	}

	public static JedisPool getSlavePool() {
		JedisPool pool = null;
		if (slavePool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			pool = new JedisPool(config, "10.91.230.233", 6379);
		}
		return pool;
	}

}
