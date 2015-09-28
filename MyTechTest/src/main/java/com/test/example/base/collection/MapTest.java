package com.test.example.base.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class MapTest {

	public static void main(String[] args) {
		linkedHashMapTest();
		hashMapTest();
		treeMapTest();
		treeMapTest2();
		treeMapTest3();
	}
	
	private static void synchronizedMapTest() {
		Map m = Collections.synchronizedMap(new HashMap());
	    List l = Collections.synchronizedList(new ArrayList());
	    // put-if-absent idiom -- contains a race condition
	    // may require external synchronization
//	    if (!map.containsKey(key))
//	      map.put(key, value);
	    // ad-hoc iteration -- contains race conditions
	    // may require external synchronization
//	    for (int i=0; i<list.size(); i++) {
//	      doSomething(list.get(i));
//	    }
	    // normal iteration -- can throw ConcurrentModificationException
	    // may require external synchronization
//	    for (Iterator i=list.iterator(); i.hasNext(); ) {
//	      doSomething(i.next());
//	    }
	}

	/**
	 * LinkedHashMap是HashMap的一个子类，保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，
	 * 默认得到的记录肯定是先插入的
	 */
	private static void linkedHashMapTest() {
		Map<String, String> m = new LinkedHashMap<String, String>();
		m.put("a", "a");
		m.put("b", "b");
		m.put("d", "d");
		m.put("c", "c");
		for (Iterator<String> it = m.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			System.out.println(key + "=" + m.get(key));
		}
		System.out.println();
	}

	/**
	 * Hashmap是一个最常用的Map，它根据键的HashCode值存储数据，根据键可以直接获取它的值，具有很快的访问速度，遍历时，
	 * 取得数据的顺序是完全随机的
	 */
	private static void hashMapTest() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("a", "a");
		m.put("b", "b");
		m.put("d", "d");
		m.put("c", "c");
		for (Iterator<String> it = m.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			System.out.println(key + "=" + m.get(key));
		}
		System.out.println();
	}

	/**
	 * TreeMap实现SortMap接口，能够把它保存的记录根据键排序，默认是按键值的升序排序
	 */
	private static void treeMapTest() {
		TreeMap<String, String> m = new TreeMap<String, String>();
		m.put("a", "a");
		m.put("b", "b");
		m.put("d", "d");
		m.put("c", "c");
		for (Iterator<String> it = m.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			System.out.println(key + "=" + m.get(key));
		}
		System.out.println();
	}

	/**
	 * TreeMap实现SortMap接口，能够把它保存的记录根据键排序，默认是按键值的升序排序，也可以倒序
	 */
	private static void treeMapTest2() {
		TreeMap<String, String> m = new TreeMap<String, String>();
		m.put("a", "a");
		m.put("b", "b");
		m.put("d", "d");
		m.put("c", "c");
		for (Iterator<String> it = m.descendingKeySet().iterator(); it
				.hasNext();) {
			String key = it.next();
			System.out.println(key + "=" + m.get(key));
		}
		System.out.println();
	}

	private static void treeMapTest3() {
		// 不指定排序器
		TreeMap<String, String> treeMap1 = new TreeMap<String, String>();
		treeMap1.put("2", "1");
		treeMap1.put("b", "1");
		treeMap1.put("1", "1");
		treeMap1.put("a", "1");
		for (Iterator<String> it = treeMap1.keySet().iterator(); it
				.hasNext();) {
			String key = it.next();
			System.out.println(key + "=" + treeMap1.get(key));
		}
		System.out.println();

		// 指定排序器
		TreeMap<Person, Integer> treeMap2 = new TreeMap<Person, Integer>(
				new Comparator<Person>() {
					public int compare(Person o1, Person o2) {
						return o1.getBirthDate().compareTo(o2.getBirthDate());
					}

					@Override
					public Comparator<Person> reversed() {
						return null;
					}

					@Override
					public Comparator<Person> thenComparing(
							Comparator<? super Person> arg0) {
						return null;
					}

					@Override
					public <U extends Comparable<? super U>> Comparator<Person> thenComparing(
							Function<? super Person, ? extends U> arg0) {
						return null;
					}

					@Override
					public <U> Comparator<Person> thenComparing(
							Function<? super Person, ? extends U> arg0,
							Comparator<? super U> arg1) {
						return null;
					}

					@Override
					public Comparator<Person> thenComparingDouble(
							ToDoubleFunction<? super Person> arg0) {
						return null;
					}

					@Override
					public Comparator<Person> thenComparingInt(
							ToIntFunction<? super Person> arg0) {
						return null;
					}

					@Override
					public Comparator<Person> thenComparingLong(
							ToLongFunction<? super Person> arg0) {
						return null;
					}
				});

		Person p1 = new Person();
		p1.setBirthDate(new Date(System.currentTimeMillis()));
		p1.setName("Mike");
		treeMap2.put(p1, 1);

		Person p2 = new Person();
		p2.setBirthDate(new Date(System.currentTimeMillis() + 5000));
		p2.setName("Jack");
		treeMap2.put(p2, 2);
		
		Person p3 = new Person();
		p3.setBirthDate(new Date(System.currentTimeMillis() - 5000));
		p3.setName("Rose");
		treeMap2.put(p3, 3);
		
		for (Iterator<Person> it = treeMap2.keySet().iterator(); it
				.hasNext();) {
			Person key = it.next();
			System.out.println(key.getName() + "=" + treeMap2.get(key));
		}
		System.out.println();
	}

}

class Person {

	private String name;

	private Date birthDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
