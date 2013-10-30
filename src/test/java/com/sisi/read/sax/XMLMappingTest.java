package com.sisi.read.sax;

import junit.framework.Assert;

import org.junit.Test;

import com.sisi.read.Mapping;

/**
 * @author kim 2013-10-25
 */
public class XMLMappingTest {

	@Test
	public void testMapping() {
		Mapping mapping = new XMLMapping();
		Assert.assertEquals(A.class, mapping.newInstance("test1", "A").getClass());
		Assert.assertEquals(A.class, mapping.newInstance("test2", "A").getClass());
		Assert.assertEquals(A.class, mapping.newInstance("test1", "A1").getClass());
		Assert.assertEquals(B.class, mapping.newInstance("test1", "B").getClass());
		Assert.assertEquals(C.class, mapping.newInstance(null, "B").getClass());
		Assert.assertEquals(C.class, mapping.newInstance("test1", null).getClass());
	}

	public static class A {
	}

	public static class B {
	}

	public static class C {
	}
}
