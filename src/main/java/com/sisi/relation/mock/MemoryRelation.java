package com.sisi.relation.mock;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.LRUMap;

import com.sisi.context.JID;
import com.sisi.relation.Relation;

/**
 * @author kim 2013-11-1
 */
public class MemoryRelation implements Relation {

	private final static List<JID> EMPTY = new ArrayList<JID>();

	private LRUMap relations;

	public MemoryRelation(Integer poolSize) {
		this.relations = new LRUMap(poolSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JID> relation(JID jid) {
		List<JID> jids = (List<JID>) this.relations.get(jid.getUser());
		return jids != null ? jids : EMPTY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JID> relation(JID from, JID to) {
		List<JID> jids = (List<JID>) this.relations.get(from.getUser());
		if (jids == null) {
			jids = new ArrayList<JID>();
		}
		jids.add(to);
		this.relations.put(from.getUser(), jids);
		return jids;
	}
}
