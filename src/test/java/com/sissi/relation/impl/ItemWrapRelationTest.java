package com.sissi.relation.impl;

import junit.framework.Assert;

import org.junit.Test;

import com.sissi.protocol.iq.roster.Item;

/**
 * @author kim 2013-11-12
 */
public class ItemWrapRelationTest {

	@Test
	public void testFillData() {
		Item item = new Item();
		item.setJid("A");
		item.setName("B");
		item.setGroup("C");
		item.setSubscription("D");
		ItemWrapRelation relation = new ItemWrapRelation(item);
		Assert.assertEquals(4, relation.toEntity().size());
		Assert.assertEquals("A", relation.toEntity().get("jid"));
		Assert.assertEquals("B", relation.toEntity().get("name"));
		Assert.assertEquals("C", relation.toEntity().get("group"));
		Assert.assertEquals("D", relation.toEntity().get("state"));
	}
}
