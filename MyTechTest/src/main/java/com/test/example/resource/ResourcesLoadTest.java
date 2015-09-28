package com.test.example.resource;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ResourcesLoadTest {

	public static void main(String[] args) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			Resource[] resources = resolver
					.getResources("classpath*:*/**/*.xml");
			for (Resource res : resources) {
				if (res.getFilename().endsWith("xml")) {
					System.out.println(res.getURI());
					System.out.println(res.getFilename());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
