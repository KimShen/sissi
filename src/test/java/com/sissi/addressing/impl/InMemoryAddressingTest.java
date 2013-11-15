package com.sissi.addressing.impl;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;

import com.sissi.context.JIDContext;
import com.sissi.context.impl.User;

/**
 * @author kim 2013-11-1
 */
public class InMemoryAddressingTest {

	@Test
	public void testJoinAndFind() {
		InMemoryAddressing inMemory = new InMemoryAddressing(100);
		JIDContext one = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(one.jid()).andReturn(new User("test1@t.com")).anyTimes();
		JIDContext two = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(two.jid()).andReturn(new User("test2@t.com")).anyTimes();
		JIDContext three = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(three.jid()).andReturn(new User("test3@t.com")).anyTimes();
		JIDContext four = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(four.jid()).andReturn(new User("test4@t.com")).anyTimes();
		EasyMock.replay(one);
		EasyMock.replay(two);
		EasyMock.replay(three);
		EasyMock.replay(four);
		inMemory.join(one);
		inMemory.join(two);
		inMemory.join(three);
		Assert.assertSame(one, inMemory.find(new User("test1@t.com")));
		Assert.assertSame(two, inMemory.find(new User("test2@t.com")));
		Assert.assertSame(three, inMemory.find(new User("test3@t.com")));
		Assert.assertSame(null, inMemory.find(new User("test4@t.com")));
	}
	
	@Test
	public void testJoinSameJIDAndMerge() {
		InMemoryAddressing inMemory = new InMemoryAddressing(100);
		JIDContext one = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(one.jid()).andReturn(new User("test1@t.com")).anyTimes();
		JIDContext two = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(two.jid()).andReturn(new User("test1@t.com")).anyTimes();
		EasyMock.replay(one);
		EasyMock.replay(two);
		inMemory.join(one);
		Assert.assertSame(one, inMemory.find(new User("test1@t.com")));
		inMemory.join(two);
		Assert.assertSame(two, inMemory.find(new User("test1@t.com")));
	}
	
	@Test
	public void testLRU() {
		InMemoryAddressing inMemory = new InMemoryAddressing(2);
		JIDContext one = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(one.jid()).andReturn(new User("test1@t.com")).anyTimes();
		JIDContext two = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(two.jid()).andReturn(new User("test2@t.com")).anyTimes();
		JIDContext three = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(three.jid()).andReturn(new User("test3@t.com")).anyTimes();
		EasyMock.replay(one);
		EasyMock.replay(two);
		EasyMock.replay(three);
		inMemory.join(one);
		inMemory.join(two);
		Assert.assertSame(one, inMemory.find(new User("test1@t.com")));
		Assert.assertSame(two, inMemory.find(new User("test2@t.com")));
		inMemory.join(three);
		Assert.assertSame(three, inMemory.find(new User("test3@t.com")));
		Assert.assertSame(null, inMemory.find(new User("test1@t.com")));
	}
	
	@Test
	public void testIsOnline() {
		InMemoryAddressing inMemory = new InMemoryAddressing(100);
		JIDContext one = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(one.jid()).andReturn(new User("test1@t.com")).anyTimes();
		JIDContext two = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(two.jid()).andReturn(new User("test2@t.com")).anyTimes();
		JIDContext three = EasyMock.createMock(JIDContext.class);
		EasyMock.expect(three.jid()).andReturn(new User("test3@t.com")).anyTimes();
		EasyMock.replay(one);
		EasyMock.replay(two);
		EasyMock.replay(three);
		inMemory.join(one);
		inMemory.join(two);
		inMemory.join(three);
		Assert.assertTrue(inMemory.isOnline(new User("test1@t.com")));
		Assert.assertTrue(inMemory.isOnline(new User("test2@t.com")));
		Assert.assertTrue(inMemory.isOnline(new User("test3@t.com")));
		Assert.assertFalse(inMemory.isOnline(new User("test4@t.com")));
	}
}
