package com.test.example.base.jdk8;


public class Jdk8Test {

//	public static void main(String[] args) {
//		// test1();
//		// StreamTest();
//		// ArraysSortTest();
//		// OptionalTest();
//		// SupplierTest();
//		// ConsumerTest();
//		// MapForeachTest();
//		// MultiAnnotationTest();
//		FunctionalInterfaceTest();
//	}
//
//	public static void FunctionalInterfaceTest() {
//		Converter<String, Integer> converter = (a) -> Integer.valueOf(a);
//		Integer converted = converter.convert("123");
//		System.out.println(converted); // 123
//
//		Converter<Person, String> personConverter = (a) -> a.firstName + " "
//				+ a.lastName;
//		String fn = personConverter.convert(new Person("Michel", "joden"));
//		System.out.println(fn);
//
//		converter = Integer::valueOf;
//		converted = converter.convert("123");
//		System.out.println(converted); // 123
//		
//		converter = new Person() :: getNameLength;
//		converted = converter.convert("123");
//		System.out.println(converted); // 3
//	}
//
//	public static void MultiAnnotationTest() {
//		Hint hint = Teacher.class.getAnnotation(Hint.class);
//		System.out.println(hint); // null
//		Hints hints1 = Teacher.class.getAnnotation(Hints.class);
//		System.out.println(hints1.value().length); // 2
//		Hint[] hints2 = Teacher.class.getAnnotationsByType(Hint.class);
//		System.out.println(hints2.length); // 2
//	}
//
//	public static void MapForeachTest() {
//
//		Map<Integer, String> map = new HashMap<>();
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
//	public static void ConsumerTest() {
//		Consumer<Person> greeter = (p) -> System.out.println("Hello, "
//				+ p.firstName);
//		greeter.accept(new Person("Luke", "Skywalker"));
//	}
//
//	public static void SupplierTest() {
//
//		Supplier<Person> personSupplier = Person::new;
//		Person p1 = personSupplier.get(); // new Person
//		Person p2 = personSupplier.get(); // new Person
//		System.out.println(p1);
//		System.out.println(p2);
//	}
//
//	public static void OptionalTest() {
//		Optional<String> optional = Optional.of("bam");
//		optional.isPresent(); // true
//		optional.get(); // "bam"
//		optional.orElse("fallback"); // "bam"
//		optional.ifPresent((s) -> System.out.println(s.charAt(0))); // "b"
//	}
//
//	public static void ArraysSortTest() {
//		int max = 1000000;
//		List<String> values = new ArrayList<>(max);
//		for (int i = 0; i < max; i++) {
//			UUID uuid = UUID.randomUUID();
//			values.add(uuid.toString());
//		}
//
//		long start = System.nanoTime();
//		long count = values.parallelStream().sorted().count();
//		System.out.println(count);
//		long end = System.nanoTime();
//		long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
//		System.out
//				.println(String.format("sequential sort took: %d ms", millis));
//
//		start = System.nanoTime();
//		count = values.stream().sorted().count();
//		end = System.nanoTime();
//		millis = TimeUnit.NANOSECONDS.toMillis(end - start);
//		System.out
//				.println(String.format("sequential sort took: %d ms", millis));
//	}
//
//	public static void StreamTest() {
//
//		List<String> stringCollection = new ArrayList<>();
//		stringCollection.add("ddd2");
//		stringCollection.add("aaa2");
//		stringCollection.add("bbb1");
//		stringCollection.add("aaa1");
//		stringCollection.add("bbb3");
//		stringCollection.add("ccc");
//		stringCollection.add("bbb2");
//		stringCollection.add("ddd1");
//
//		stringCollection.parallelStream().filter((s) -> s.startsWith("a"))
//				.forEach(System.out::println);
//	}
//
//	public static void test1() {
//		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
//
//		names.forEach(System.out::println);
//		System.out.println();
//
//		Collections.sort(names, (String a, String b) -> {
//			return a.compareTo(b);
//		});
//		names.forEach(System.out::println);
//		System.out.println();
//
//		Collections.sort(names, (String a, String b) -> b.compareTo(a));
//		names.forEach(System.out::println);
//		System.out.println();
//
//		Collections.sort(names, (a, b) -> a.compareTo(b));
//		names.forEach(System.out::println);
//		System.out.println();
//	}

}
