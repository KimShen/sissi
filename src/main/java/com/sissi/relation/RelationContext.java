package com.sissi.relation;

import java.util.Set;

import com.sissi.context.JID;

/**
 * @author kim 2013-11-13
 */
public interface RelationContext {

	public void subscribe(JID from, Relation relation);

	public void subscribed(JID from, JID to);

	public void unsubscribed(JID from, JID to);

	public Relation ourRelation(JID from, JID to);

	public Boolean weAreFriends(JID from, JID to);

	public Set<Relation> myRelations(JID from);

	public Set<String> whoSubscribedMe(JID from);

	public Set<String> iSubscribedWho(JID from);
}