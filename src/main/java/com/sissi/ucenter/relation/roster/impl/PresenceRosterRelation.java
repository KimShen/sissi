package com.sissi.ucenter.relation.roster.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * Presence Item包装
 * 
 * @author kim 2014年1月23日
 */
public class PresenceRosterRelation implements RosterRelation {

	private final JID jid;

	private final RosterRelation relation;

	public PresenceRosterRelation(JID jid, Presence presence, RosterRelation relation) {
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
	public String subscription() {
		return this.relation.subscription();
	}

	public boolean in(String... subscriptions) {
		return RosterSubscription.parse(this.relation.subscription()).in(subscriptions);
	}

	@Override
	public boolean activate() {
		return this.relation.activate();
	}

	@Override
	public boolean ask() {
		return true;
	}

	@Override
	public String[] groups() {
		return null;
	}

	@Override
	public <T extends Relation> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}

	@Override
	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put(Dictionary.FIELD_NAME, this.jid.user());
		plus.put(Dictionary.FIELD_ACK, this.ask());
		return plus;
	}
}
