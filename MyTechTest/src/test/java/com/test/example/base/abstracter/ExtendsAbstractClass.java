package com.test.example.base.abstracter;

import org.junit.Assert;
import org.junit.Test;

public class ExtendsAbstractClass extends AbstractClass {

	@Test
	public void testIsAssignFrom() {
		Assert.assertTrue(AbstractClass.class
				.isAssignableFrom(ExtendsAbstractClass.class));
	}

}
