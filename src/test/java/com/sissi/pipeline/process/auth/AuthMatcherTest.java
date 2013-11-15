package com.sissi.pipeline.process.auth;

import junit.framework.Assert;

import org.junit.Test;

import com.sissi.pipeline.process.ClassMatcher;
import com.sissi.protocol.auth.Auth;
import com.sissi.protocol.stream.Stream;

/**
 * @author kim 2013-11-12
 */
public class AuthMatcherTest {

	@Test
	public void testIsMatch() {
		ClassMatcher auth = new ClassMatcher(Auth.class);
		Assert.assertTrue(auth.match(new Auth()));
	}

	@Test
	public void testNotMatch() {
		ClassMatcher auth = new ClassMatcher(Auth.class);
		Assert.assertFalse(auth.match(new Stream()));
	}
}
