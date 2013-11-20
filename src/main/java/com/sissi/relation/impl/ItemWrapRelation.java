package com.sissi.relation.impl;

import java.util.Map;

import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.presence.Presence.Subscribe;
import com.sissi.relation.P2PRelation;
import com.sissi.relation.RelationMapping;

/**
 * @author kim 2013-11-6
 */
public class ItemWrapRelation extends RelationMapping implements P2PRelation {

	private Item item;

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
	public String group() {
		return this.item.getGroup();
	}

	@Override
	public String getSubscription() {
		return Subscribe.parse(this.item.getSubscription()).toString();
	}

	public Map<String, Object> toEntity() {
		Map<String, Object> entity = super.toEntity();
		entity.put("group", this.group());
		return entity;
	}
}
