package com.sissi.ucenter;

import java.util.Map;
import java.util.Set;

import com.sissi.context.JID;

/**
 * @author kim 2013-11-13
 */
public interface RelationContext {

	public RelationContext establish(JID from, Relation relation);

	public RelationContext update(JID from, JID to, String state);

	public RelationContext remove(JID from, JID to);

	public Relation ourRelation(JID from, JID to);

	public Set<Relation> myRelations(JID from);

	public Set<String> whoSubscribedMe(JID from);

	public Set<String> iSubscribedWho(JID from);

	public interface Relation {

		public String getJID();

		public String getName();

		public String getSubscription();

		public Map<String, Object> plus();
	}

	public interface RelationRoster extends Relation {

		public String asGroup();
	}
}