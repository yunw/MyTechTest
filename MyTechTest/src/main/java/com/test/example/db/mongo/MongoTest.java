package com.test.example.db.mongo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

public class MongoTest {

	private static Properties prop = new Properties();

	private static DB db = null;

	static {
		try (InputStream is = MongoTest.class.getResourceAsStream("/mongodb.properties");) {
			prop.load(is);
			String host = prop.getProperty("host");
			Integer port = Integer.parseInt(prop.getProperty("port"));
			List<ServerAddress> saList = new ArrayList<>();
			saList.add(new ServerAddress(host, port));
			List<MongoCredential> mcList = new ArrayList<>();
			String dbname = prop.getProperty("dbname");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			mcList.add(MongoCredential.createCredential(username, dbname, password.toCharArray()));
			MongoClientOptions.Builder builder = MongoClientOptions.builder();
			builder.description(prop.getProperty("description"));
			builder.minConnectionsPerHost(Integer.parseInt(prop.getProperty("minConnectionsPerHost")));
			builder.connectionsPerHost(Integer.parseInt(prop.getProperty("connectionsPerHost")));
			builder.threadsAllowedToBlockForConnectionMultiplier(
			        Integer.parseInt(prop.getProperty("threadsAllowedToBlockForConnectionMultiplier")));
			MongoClient client = new MongoClient(saList, mcList, builder.build());
			db = client.getDB(prop.getProperty("dbname"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		// 查询所有的聚集集合
		for (String name : db.getCollectionNames()) {
			System.out.println("collectionName: " + name);
		}

		DBCollection users = db.getCollection("users");
		
		DBObject user = new BasicDBObject();
	    user.put("name", "hoojo");
	    user.put("age", 24);
	    add(users, user);

		// 查询所有的数据
		DBCursor cur = users.find();
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
		System.out.println(cur.count());
		System.out.println(cur.getCursorId());
		System.out.println(JSON.serialize(cur));
	}

	public static void add(DBCollection collection, DBObject dbObject) {
		collection.insert(dbObject);
	}

}
