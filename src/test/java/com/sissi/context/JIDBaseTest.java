package com.sissi.context;

import junit.framework.Assert;

import org.junit.Test;

import com.sissi.context.impl.User;

/**
 * @author kim 2013-11-12
 */
public class JIDBaseTest {

	@Test
	public void testJIDWithOutjidBase() {
		User jidBase = new JIDBaseImpl("www.chat.com");
		Assert.assertEquals(null, jidBase.user());
		Assert.assertEquals("www.chat.com", jidBase.host());
		Assert.assertEquals(null, jidBase.resource());
		Assert.assertEquals("www.chat.com", jidBase.asStringWithBare());
		Assert.assertEquals("www.chat.com", jidBase.asString());
	}

	@Test
	public void testJIDWithOutResoudxe() {
		User jidBase = new JIDBaseImpl("kim@www.chat.com");
		Assert.assertEquals("kim", jidBase.user());
		Assert.assertEquals("www.chat.com", jidBase.host());
		Assert.assertEquals(null, jidBase.resource());
		Assert.assertEquals("kim@www.chat.com", jidBase.asStringWithBare());
		Assert.assertEquals("kim@www.chat.com", jidBase.asString());
	}

	@Test
	public void testJIDWithResource() {
		User jidBase = new JIDBaseImpl("kim@www.chat.com/www.myaccount.com");
		Assert.assertEquals("kim", jidBase.user());
		Assert.assertEquals("www.chat.com", jidBase.host());
		Assert.assertEquals("www.myaccount.com", jidBase.resource());
		Assert.assertEquals("kim@www.chat.com", jidBase.asStringWithBare());
		Assert.assertEquals("kim@www.chat.com/www.myaccount.com", jidBase.asString());
	}

	@Test
	public void testJIDWithResourceIncludeAt() {
		User jidBase = new JIDBaseImpl("kim@www.chat.com/test@www.myaccount.com");
		Assert.assertEquals("kim", jidBase.user());
		Assert.assertEquals("www.chat.com", jidBase.host());
		Assert.assertEquals("test@www.myaccount.com", jidBase.resource());
		Assert.assertEquals("kim@www.chat.com", jidBase.asStringWithBare());
		Assert.assertEquals("kim@www.chat.com/test@www.myaccount.com", jidBase.asString());
	}

	private static class JIDBaseImpl extends User {

		public JIDBaseImpl(String jid) {
			super(jid);
		}

		public JIDBaseImpl(String jidBase, String host) {
			super(jidBase, host);
		}

		public JIDBaseImpl(String jidBase, String host, String resource) {
			super(jidBase, host, resource);
		}
	}
}
