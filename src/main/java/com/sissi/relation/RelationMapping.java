package com.sissi.relation;

import java.util.Map;

import com.google.common.collect.Maps;
import com.sissi.relation.Relation;

/**
 * @author kim 2013-11-6
 */
public abstract class RelationMapping implements Relation {

	private Map<String, Object> entity = Maps.newHashMap();

	@Override
	public Map<String, Object> toEntity() {
		entity.clear();
		entity.put("jid", this.getJID());
		entity.put("name", this.getName());
		entity.put("state", this.getSubscription());
		return entity;
	}
}
