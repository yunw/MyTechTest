package com.test.example.base.jdk7;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class Jdk7Test {

	public static void main(String[] args) {
		// switchString("one");
		// binaryLiteralsTest(0B1011_1010);
		copyFile("E:\\yinsl\\VM\\VMware-viewclient-x32-4.6.0-366101.rar",
				"E:\\yinsl\\VM\\VMware-viewclient-x32-4.6.0-366101.rar.bak");
	}

	public static void copyFile(String source, String target) {
		long start = System.nanoTime();
		try (InputStream in = new FileInputStream(source);
				OutputStream out = new FileOutputStream(target)) {
			byte[] bb = new byte[4096];
			int n = 0;
			while ((n = in.read(bb)) >= 0) {
				out.write(bb, 0, n);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		long end = System.nanoTime();
		long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
		System.out.println("copy file used " + millis + "ms");
	}

	public static void binaryLiteralsTest(int i) {
		System.out.println(i);
	}

	public static void switchString(String s) {
		switch (s) {
		case "one":
			System.out.println(1);
			break;
		case "two":
			System.out.println(2);
			break;
		case "three":
			System.out.println(3);
			break;
		default:
			System.out.println("error");
		}
	}

}
