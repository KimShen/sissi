package com.sissi.relation.impl;

import junit.framework.Assert;

import org.junit.Test;

import com.sissi.relation.RelationMapping;

/**
 * @author kim 2013-11-12
 */
public class RelationMappingTest {

	@Test
	public void testReturnSameEntity() {
		RelationMappingImpl mapping = new RelationMappingImpl("A", "B", "C");
		Assert.assertSame(mapping.toEntity(), mapping.toEntity());
	}

	@Test
	public void testMappingAttr() {
		RelationMappingImpl mapping = new RelationMappingImpl("A", "B", "C");
		Assert.assertEquals("A", mapping.getJID());
		Assert.assertEquals("B", mapping.getName());
		Assert.assertEquals("C", mapping.getSubscription());
	}

	@Test
	public void testMappingAttrWithSomeChange() {
		RelationMappingImpl mapping = new RelationMappingImpl("A", "B", "C");
		Assert.assertEquals("A", mapping.getJID());
		Assert.assertEquals("B", mapping.getName());
		Assert.assertEquals("C", mapping.getSubscription());
		mapping.setJid("D");
		Assert.assertEquals("D", mapping.getJID());
	}

	private static class RelationMappingImpl extends RelationMapping {

		private String jid;

		private String name;

		private String subscription;

		public RelationMappingImpl(String jid, String name, String subscription) {
			super();
			this.jid = jid;
			this.name = name;
			this.subscription = subscription;
		}

		public String setJid(String jid) {
			this.jid = jid;
			return this.jid;
		}

		public String getJID() {
			return jid;
		}

		public String getName() {
			return name;
		}

		public String getSubscription() {
			return subscription;
		}
	}
}
