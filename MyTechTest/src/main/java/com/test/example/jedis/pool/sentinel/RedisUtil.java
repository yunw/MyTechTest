package com.test.example.jedis.pool.sentinel;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class RedisUtil {

	private static Logger logger = Logger.getLogger(RedisUtil.class);

	private static Properties prop = new Properties();

	private static int EXPIRE_SECONDS = 1800;

	private static JedisSentinelPool sentinelPool;

	static {
		try (InputStream is = RedisUtil.class.getResourceAsStream("/config/redis.properties");) {
			prop.load(is);

			Set<String> sentinels = new HashSet<String>();
			sentinels.add(new HostAndPort(prop.getProperty("master.host"), Integer.parseInt(prop
			        .getProperty("master.port"))).toString());
			sentinelPool = new JedisSentinelPool("mymaster1", sentinels);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public static void main(String[] args) {
		for (int i = 20; i < 30; i++) {
//			set("key" + i, "value" + i);
			System.out.println(get("key" + i));
		}

	}

	public static void set(String key, String value) {
		long start = System.currentTimeMillis();
		try (Jedis jedis = sentinelPool.getResource();) {
			jedis.setex(key, EXPIRE_SECONDS, value);
		} catch (Exception e) {
			logger.error("set::::" + e);
		}
		long end = System.currentTimeMillis();
		logger.error("redis set" + (end - start) / 1000 + "s");
	}

	public static void hset(String key, String feild, String value) {
		long start = System.currentTimeMillis();
		try (Jedis jedis = sentinelPool.getResource();) {
			logger.debug("redis hset start, key: " + key + ", feild: " + feild + ", value: " + value);
			jedis.hset(key, feild, value);
			logger.debug("redis hset success, key: " + key + ", feild: " + feild + ", value: " + value);
		} catch (Exception e) {
			logger.error("hset::::" + e);
		}
		long end = System.currentTimeMillis();
		logger.debug("redis hset " + (end - start) / 1000 + "s");
	}

	public static String hget(String key, String feild) {
		String result = null;
		try (Jedis jedis = sentinelPool.getResource();) {
			result = jedis.hget(key, feild);
		} catch (Exception e) {
			logger.error("hget::::" + e);
		}
		return result;
	}

	public static Map<String, String> hgetAll(String key) {
		Map<String, String> result = null;
		try (Jedis jedis = sentinelPool.getResource();) {
			result = jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error("hget::::" + e);
		}
		return result;
	}

	public static String get(String key) {
		String result = null;
		try (Jedis jedis = sentinelPool.getResource();) {
			result = jedis.get(key);
		} catch (Exception e) {
			logger.error("get::::" + e);
		}
		return result;
	}

	public static void del(String key, String feild) {
		try (Jedis jedis = sentinelPool.getResource();) {
			jedis.del(key, feild);
		} catch (Exception e) {
			logger.error("del::::" + e);
		}
	}

	public static void hdel(String key, String feild) {

		try (Jedis jedis = sentinelPool.getResource();) {
			jedis.hdel(key, feild);
		} catch (Exception e) {
			logger.error("hdel::::" + e);
		}
	}

}

