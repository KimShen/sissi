package com.sisi.relation;

import java.util.List;

import com.sisi.context.JID;

/**
 * @author kim 2013-11-1
 */
public interface RelationContext {

	public List<Relation> relation(JID jid);

	public List<Relation> relation(Relation relation);
}
