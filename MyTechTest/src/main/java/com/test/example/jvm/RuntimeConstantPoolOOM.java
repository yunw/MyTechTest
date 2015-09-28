package com.test.example.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:MaxPermSize=5m -Xmx10m
 * @author Administrator
 *
 */
public class RuntimeConstantPoolOOM {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while(true) {
			list.add(String.valueOf(i++).intern());
		}
	}

}
