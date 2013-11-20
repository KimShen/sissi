package com.sissi.relation;

import java.util.Set;

import com.sissi.context.JID;

/**
 * @author kim 2013-11-13
 */
public interface RelationContext {

<<<<<<< HEAD
	public void establish(JID from, Relation relation);

	public void update(JID from, JID to, String state);

	public void remove(JID from, JID to);

	public Relation ourRelation(JID from, JID to);

=======
	public void subscribe(JID from, Relation relation);

	public void subscribed(JID from, JID to);

	public void unsubscribed(JID from, JID to);

	public Relation ourRelation(JID from, JID to);

	public Boolean weAreFriends(JID from, JID to);

>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	public Set<Relation> myRelations(JID from);

	public Set<String> whoSubscribedMe(JID from);

	public Set<String> iSubscribedWho(JID from);
}