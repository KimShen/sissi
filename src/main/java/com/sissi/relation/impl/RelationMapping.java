package com.sissi.relation.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.sissi.relation.Relation;

/**
 * @author kim 2013-11-6
 */
public abstract class RelationMapping implements Relation {

	@Override
	public Map<String, Object> toEntity() {
		Map<String, Object> entity = Maps.newHashMap();
		entity.put("jid", this.getJid());
		entity.put("name", this.getName());
		entity.put("group", this.getGroup());
		entity.put("state", this.getSubscription());
		return entity;
	}
}
