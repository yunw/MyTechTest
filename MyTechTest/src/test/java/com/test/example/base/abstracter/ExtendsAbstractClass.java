package com.test.example.base.abstracter;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class ExtendsAbstractClass extends AbstractClass {

	@Test
	public void testIsAssignFrom() {
		Assert.assertTrue(AbstractClass.class
				.isAssignableFrom(ExtendsAbstractClass.class));
	}
	
	public static void main(String[] args) {
		for (int i =0; i < 1000;i++) {
			System.out.println(UUID.randomUUID().toString());
		}
	}

}
