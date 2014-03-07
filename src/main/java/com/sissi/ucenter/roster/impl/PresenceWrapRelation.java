package com.sissi.ucenter.roster.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.roster.RelationRoster;

/**
 * @author kim 2014年1月23日
 */
public class PresenceWrapRelation implements RelationRoster {

	private final JID jid;

	private final RelationRoster relation;

	public PresenceWrapRelation(JID jid, Presence presence, RelationRoster relation) {
		super();
		this.jid = jid;
		this.relation = relation;
	}

	@Override
	public String jid() {
		return this.jid.asStringWithBare();
	}

	@Override
	public String name() {
		return this.jid.resource();
	}

	@Override
	public String getSubscription() {
		return this.relation.getSubscription();
	}

	public boolean in(String... subscriptions) {
		return RosterSubscription.parse(this.relation.getSubscription()).in(subscriptions);
	}

	@Override
	public boolean activate() {
		return this.relation.activate();
	}

	@Override
	public boolean isAsk() {
		return true;
	}

	@Override
	public String[] asGroups() {
		return null;
	}

	@Override
	public <T extends Relation> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}

	@Override
	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("name", this.jid.user());
		plus.put("ask", this.isAsk());
		return plus;
	}
}
