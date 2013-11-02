package com.sissi.relation.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.LRUMap;

import com.sissi.context.JID;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-1
 */
public class MemoryRelationContext implements RelationContext {

	private final static List<Relation> EMPTY = new ArrayList<Relation>();

	private LRUMap relations;

	public MemoryRelationContext(Integer poolSize) {
		this.relations = new LRUMap(poolSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Relation> relation(JID jid) {
		List<Relation> relations = (List<Relation>) this.relations.get(jid.getUser());
		return relations != null ? relations : EMPTY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Relation> relation(Relation relation) {
		List<Relation> relations = (List<Relation>) this.relations.get(relation.from().getUser());
		if (relations == null) {
			relations = new ArrayList<Relation>();
		}
		relations.add(relation);
		this.relations.put(relation.from().getUser(), relations);
		return relations;
	}
}
