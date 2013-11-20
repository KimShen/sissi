package com.sissi.relation.impl;

import com.sissi.context.JID;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationMapping;

/**
 * @author kim 2013-11-13
 */
public class GroupWrapRelation extends RelationMapping implements Relation {

	private JID jid;

	public GroupWrapRelation(JID jid) {
		super();
		this.jid = jid;
	}

	@Override
	public String getJID() {
		return this.jid.asStringWithBare();
	}

	@Override
	public String getName() {
		return this.jid.resource();
	}

	@Override
	public String getSubscription() {
		return State.SUBSCRIBE.toString();
	}
}
