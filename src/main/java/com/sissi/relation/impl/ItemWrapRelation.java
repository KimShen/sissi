package com.sissi.relation.impl;

import com.sissi.protocol.iq.roster.Item;

/**
 * @author kim 2013-11-6
 */
public class ItemWrapRelation extends RelationMapping {

	private Item item;

	public ItemWrapRelation(Item item) {
		super();
		this.item = item;
	}

	@Override
	public String getJid() {
		return this.item.getJid();
	}

	@Override
	public String getName() {
		return this.item.getName();
	}

	@Override
	public String getGroup() {
		return this.item.getGroup();
	}
	
	@Override
	public String getSubscription() {
		return this.item.getSubscription();
	}
}
