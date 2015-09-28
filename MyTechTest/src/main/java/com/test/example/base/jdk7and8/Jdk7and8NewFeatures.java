package com.test.example.base.jdk7and8;


public class Jdk7and8NewFeatures {
//
//	public static void main(String[] args) {
//		switchString("one");
//		binaryLiteralsTest(0b1101_0011);
//		inferType();
//		MultiCatch();
//		copyFile("E:\\yinsl\\VM\\VMware-viewclient-x32-4.6.0-366101.rar",
//				"E:\\yinsl\\VM\\VMware-viewclient-x32-4.6.0-366101.rar.bak");
//		lambdaTest();
//		OptionalTest();
//		MapForeachTest();
//	}
//	
//	/**
//	 * map的新方法
//	 */
//	public static void MapForeachTest() {
//
//		Map<Integer, String> map = new HashMap<>();
//		map.put(0, "kkk");
//		for (int i = 0; i < 10; i++) {
//			map.putIfAbsent(i, "val" + i);
//		}
//		map.forEach((id, val) -> System.out.println(val));
//
//		map.computeIfPresent(3, (num, val) -> val + num);
//		System.out.println(map.get(3)); // val33
//		map.computeIfPresent(9, (num, val) -> null);
//		System.out.println(map.containsKey(9)); // false
//		map.computeIfAbsent(23, num -> "val" + num);
//		System.out.println(map.containsKey(23)); // true
//		map.computeIfAbsent(3, num -> "bam");
//		System.out.println(map.get(3)); // val33
//
//		map.remove(3, "val3");
//		System.out.println(map.get(3)); // val33
//		map.remove(3, "val33");
//		System.out.println(map.get(3)); // null
//
//		map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
//		System.out.println(map.get(9)); // val9
//		map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
//		System.out.println(map.get(9)); // val9concat
//	}
//
//	/**
//	 * Optional测试：如果一个方法可能返回null，建议返回Optional
//	 */
//	public static void OptionalTest() {
//		Optional<String> optional = Optional.ofNullable(null);
//		System.out.println(optional.isPresent());
//		System.out.println(optional.orElse("wo kao"));
//		optional = Optional.of("bam");
//		System.out.println(optional.isPresent());
//		System.out.println(optional.get());
//		optional.ifPresent((s) -> System.out.println(s.charAt(0)));
//	}
//
//	/**
//	 * lambda表达式的过滤和计算
//	 */
//	public static void lambdaTest() {
//		List<Student> students = new ArrayList<>();
//		Supplier<Student> studentSupplier = Student::new;
//		Student s1 = studentSupplier.get();
//		s1.setGradYear(2000);
//		s1.setScore(120);
//		students.add(s1);
//		
//		Consumer<Student> greeter = (p) -> System.out.print("grad year: "
//				+ p.getGradYear());
//		Consumer<Student> bye = (p) -> System.out.println(" score: "
//				+ p.getScore());
//		greeter.andThen(bye).accept(s1);
//		
//
//		Student s2 = studentSupplier.get();
//		s2.setGradYear(2000);
//		s2.setScore(122);
//		students.add(s2);
//		greeter.andThen(bye).accept(s2);
//
//		Student s3 = studentSupplier.get();
//		s3.setGradYear(2000);
//		s3.setScore(112);
//		students.add(s3);
//		greeter.andThen(bye).accept(s3);
//
//		Student s4 = studentSupplier.get();
//		s4.setGradYear(2001);
//		s4.setScore(112);
//		students.add(s4);
//		greeter.andThen(bye).accept(s4);
//		
//		students.stream().filter((s) -> s.getGradYear() == 2000)
//				.map((s) -> s.getScore())
//				.forEach((s) -> System.out.println("学分：" + s));
//
//		Optional<Integer> oi = students.stream()
//				.filter((s) -> s.getGradYear() == 2000)
//				.map((s) -> s.getScore()).max((o1, o2) -> o1.compareTo(o2));
//		System.out.println("最高学分： " + (oi.isPresent() ? oi.get() : null));
//	}
//
//	/**
//	 * 自动化的资源管理：实现了java.lang.AutoCloseable接口的资源不需要在finally块中关闭， 系统可以自动关闭该资源。
//	 * 
//	 * @param source
//	 * @param target
//	 */
//	public static void copyFile(String source, String target) {
//		long start = System.nanoTime();
//		try (InputStream in = new FileInputStream(source);
//				OutputStream out = new FileOutputStream(target)) {
//			byte[] bb = new byte[4096];
//			int n = 0;
//			while ((n = in.read(bb)) >= 0) {
//				out.write(bb, 0, n);
//			}
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		long end = System.nanoTime();
//		long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
//		System.out.println("copy file used " + millis + "ms");
//	}
//
//	/**
//	 * 可以一次捕获多个异常
//	 */
//	public static void MultiCatch() {
//		try {
//			Class<?> clazz = Class
//					.forName("com.test.example.base.jdk7and8.Jdk7and8NewFeatures");
//			Object obj = clazz.newInstance();
//			System.out.println(obj);
//		} catch (ClassNotFoundException | InstantiationException
//				| IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 泛型可以自动用“<>”推断其类型
//	 */
//	public static void inferType() {
//		List<String> list = new ArrayList<>();
//		list.add("aa");
//		list.add("bbb");
//		list.add("dd");
//		list.add("ccc");
//		list.forEach(System.out::println);
//	}
//
//	/**
//	 * 用二进制和“_”来表示整数
//	 * 
//	 * @param i
//	 */
//	public static void binaryLiteralsTest(int i) {
//		System.out.println(i);
//	}
//
//	/**
//	 * switch case 语句：可以用字符串来作为判断条件
//	 * 
//	 * @param s
//	 */
//	public static void switchString(String s) {
//		switch (s) {
//		case "one":
//			System.out.println(1);
//			break;
//		case "two":
//			System.out.println(2);
//			break;
//		case "three":
//			System.out.println(3);
//			break;
//		default:
//			System.out.println("error");
//		}
//	}

}
