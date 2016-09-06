package com.test.example.jedis.pool.shard;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShardedJedisPoolTest {
	
	private static int EXPIRE_SECONDS = 1800;

	private static ShardedJedisPool shardedMasterPool = getShardedMasterPool();

	private static ShardedJedisPool shardedSlavePool = getShardedSlavePool();

	public static void main(String[] args) {
		
		for (int i = 0; i < 1000; i++) {
			set("key" + i, "value" + i);
			System.out.println(get("key" + i));
		}

	}

	@SuppressWarnings("deprecation")
    public static void set(String key, String value) {
		ShardedJedis jedis = shardedMasterPool.getResource();
		try {
			jedis.setex(key, EXPIRE_SECONDS, value);
		} catch (Exception e) {
			shardedMasterPool.returnBrokenResource(jedis);
		} finally {
			shardedMasterPool.returnResource(jedis);
		}
	}

	@SuppressWarnings("deprecation")
    public static String get(String key) {
		ShardedJedis jedis = shardedSlavePool.getResource();
		String result = null;
		try {
			result = jedis.get(key);
		} catch (Exception e) {
			shardedSlavePool.returnBrokenResource(jedis);
		} finally {
			shardedSlavePool.returnResource(jedis);
		}
		return result;
	}

	public static ShardedJedisPool getShardedMasterPool() {
		ShardedJedisPool pool = null;
		if (shardedMasterPool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			JedisShardInfo info1 = new JedisShardInfo("localhost", 6379);
			JedisShardInfo info2 = new JedisShardInfo("localhost", 6380);
			List<JedisShardInfo> shardList = new ArrayList<JedisShardInfo>();
			shardList.add(info1);
			shardList.add(info2);
			pool = new ShardedJedisPool(config, shardList);
		}
		return pool;
	}

	public static ShardedJedisPool getShardedSlavePool() {
		ShardedJedisPool pool = null;
		if (shardedSlavePool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			JedisShardInfo info1 = new JedisShardInfo("localhost", 7379);
			JedisShardInfo info2 = new JedisShardInfo("localhost", 7380);
			List<JedisShardInfo> shardList = new ArrayList<JedisShardInfo>();
			shardList.add(info1);
			shardList.add(info2);
			pool = new ShardedJedisPool(config, shardList);
		}
		return pool;
	}

}
