package com.test.example.base.jdk8.compile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class Test {

	public static void main(String[] args) {
		JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
//		javac.getTask(null, null, null, options, classes, compilationUnits);
	}

}
