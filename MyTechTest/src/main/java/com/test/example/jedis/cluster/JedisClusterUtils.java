package com.test.example.jedis.cluster;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterUtils {

	private Properties prop = new Properties();

	private static JedisClusterUtils jct = new JedisClusterUtils();

	private JedisCluster jc = null;

	private JedisClusterUtils() {
		init();
	}

	public static JedisCluster getJedisCluster() {
		return jct.jc;
	}

	private void init() {
		try (InputStream is = JedisClusterUtils.class.getResourceAsStream("jedisClusterConfig.properties");) {
			prop.load(is);
			Set<HostAndPort> hpSet = new HashSet<HostAndPort>();
			for (int i = 0;; i++) {
				String host = prop.getProperty("redis.server.ip" + i);
				if (host == null) {
					break;
				}
				int port = Integer.parseInt(prop.getProperty("redis.server.port" + i));
				HostAndPort hp = new HostAndPort(host, port);
				hpSet.add(hp);
			}
			int timeout = Integer.parseInt(prop.getProperty("redis.default.commandtimeout"));
			int redirections = Integer.parseInt(prop.getProperty("redis.default.max.redirections"));
			jc = new JedisCluster(hpSet, timeout, redirections);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JedisCluster jc = JedisClusterUtils.getJedisCluster();
		for (int i = 0; i < 100; i++) {
			jc.set("key" + i, "value" + i);
		}

		for (int i = 0; i < 100; i++) {
			System.out.println(jc.get("key" + i));
		}
	}

}
