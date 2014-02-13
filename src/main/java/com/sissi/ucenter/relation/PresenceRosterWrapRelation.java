package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JIDBuilder;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月23日
 */
public class PresenceRosterWrapRelation implements RelationRoster {

	private final String jid;

	private final Relation relation;

	public PresenceRosterWrapRelation(JIDBuilder jidBuilder, Presence presence, Relation relation) {
		super();
		this.relation = relation;
		this.jid = jidBuilder.build(presence.getTo()).asStringWithBare();
	}

	@Override
	public String getJID() {
		return this.jid;
	}

	@Override
	public String getName() {
		return this.relation.getName();
	}

	@Override
	public String getSubscription() {
		return this.relation.getSubscription();
	}

	public boolean in(String... subscriptions) {
		return RosterSubscription.parse(this.relation.getSubscription()).in(subscriptions);
	}

	@Override
	public boolean in(RosterSubscription... subscriptions) {
		return RosterSubscription.parse(this.relation.getSubscription()).in(subscriptions);
	}

	@Override
	public boolean isActivate() {
		return this.relation.isActivate();
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
	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("ask", this.isAsk());
		return plus;
	}
}
