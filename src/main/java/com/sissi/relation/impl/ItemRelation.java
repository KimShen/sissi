package com.sissi.relation.impl;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sissi.context.JID;
import com.sissi.context.user.User;
import com.sissi.protocol.iq.Item;
import com.sissi.relation.Relation;

/**
 * @author kim 2013-11-1
 */
public class ItemRelation implements Relation {

	private JID from;

	private Item item;

	public ItemRelation(JID from, Item item) {
		this.from = from;
		this.item = item;
	}

	@Override
	public JID from() {
		return from;
	}

	@Override
	public JID to() {
		return new User(this.item.getJid());
	}

	@Override
	public String group() {
		return this.item.getGroup();
	}

	@Override
	public Type type() {
		return Type.parse(this.item.getType());
	}

	@Override
	public String alias() {
		return this.item.getName();
	}

	@Override
	public String getSubscription() {
		return this.item.getSubscription();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
