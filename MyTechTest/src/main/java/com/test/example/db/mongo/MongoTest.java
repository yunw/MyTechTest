package com.test.example.db.mongo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
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

	public static DBObject findOne(String collectionName, String key, Object value) {
		return db.getCollection(collectionName).findOne(new BasicDBObject(key, value));
	}

	public static DBCursor find(String collectionName, String key, Object value) {
		return db.getCollection(collectionName).find((new BasicDBObject(key, value)));
	}
	
	/**
	 * 查询满足条件的对象集
	 * 
	 * @param collectionName
	 * @param key
	 * @param valueList 查询条件
	 * @return
	 */
	public static DBCursor findIn(String collectionName, String key, List<Object> valueList) {
		BasicDBObject query =new BasicDBObject();
		query.put(key, new BasicDBObject("$in", valueList));
		return db.getCollection(collectionName).find(query);
	}
	
	/**
	 * 查询符合逻辑操作的对象集合
	 * 
	 * @param collectionName
	 * @param key
	 * @param logicOperator 例如: "$gt"、"$lt"
	 * @param value
	 * @return
	 */
	public static DBCursor findByLogicOperator(String collectionName, String logicOperator, String key, Object value) {
		BasicDBObject query =new BasicDBObject();
		query.put(key, new BasicDBObject(logicOperator, value));
		return db.getCollection(collectionName).find(query);
	}

	public static WriteResult insert(String collectionName, DBObject dbObject) {
		return db.getCollection(collectionName).insert(dbObject);
	}

	public static WriteResult insertList(String collectionName, List<DBObject> dbObjectList) {
		return db.getCollection(collectionName).insert(dbObjectList);
	}

	public static WriteResult update(String collectionName, DBObject oldObject, DBObject newObject) {
		return db.getCollection(collectionName).update(oldObject, newObject);
	}

	public static void main(String[] args) throws Exception {
//		test0();
//		 test1();
		// test2();
		// test3();
//		test4();
//		 test5();
		 test6();
	}
	
	private static void test6() {
		DBCursor cursor = findByLogicOperator("users", "$lt", "age", 24);
		while(cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}
	
	private static void test5() {
		List<Object> valueList = new ArrayList<>();
		valueList.add("n");
		valueList.add("m");
		DBCursor cursor = findIn("users", "name0", valueList);
		while(cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void test4() {
		DBObject obj = findOne("users", "name0", "hoojo0");
		System.out.println(obj);
		DBObject newObject = new BasicDBObject();
		newObject.put("name0", "n");
		newObject.put("age", 22);
		update("users", new BasicDBObject("name0", "hoojo0"), newObject);
	}

	private static void test3() {
		DBCursor cursor = find("users", "name", "hoojo");
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void test2() {
		DBObject obj = findOne("users", "name", "hoojo");
		System.out.println(obj);
	}

	private static void test1() {

		// 查询所有的数据
		DBCursor cur = db.getCollection("users").find();
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
		System.out.println(cur.count());
		System.out.println(cur.getCursorId());
		System.out.println(JSON.serialize(cur));
	}
	
	private static void test0() {
		List<DBObject> list = new ArrayList<>();
		DBObject user1 = new BasicDBObject();
		user1.put("name", "name1");
		user1.put("age", 22);
		list.add(user1);
		
		DBObject user2 = new BasicDBObject();
		user2.put("name", "name2");
		user2.put("age", 23);
		list.add(user2);
		
		DBObject user3 = new BasicDBObject();
		user3.put("name", "name3");
		user3.put("age", 24);
		list.add(user3);
		
		DBObject user4 = new BasicDBObject();
		user4.put("name", "name4");
		user4.put("age", 25);
		list.add(user4);
		
		insertList("users", list);
	}

}
