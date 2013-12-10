package com.sissi.ucenter.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster.Subscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-6
 */
public class ItemWrapRelation implements RelationRoster {

	private final Item item;

	public ItemWrapRelation(Item item) {
		super();
		this.item = item;
	}

	@Override
	public String getJID() {
		return this.item.getJid();
	}

	@Override
	public String getName() {
		return this.item.getName();
	}

	@Override
	public String asGroup() {
		Group group = this.item.getGroup();
		return group != null ? group.getText() : null;
	}

	@Override
	public String getSubscription() {
		return Subscription.parse(this.item.getSubscription()).toString();
	}

	public Boolean isBan() {
		return false;
	}

	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("group", this.asGroup());
		return plus;
	}
}
