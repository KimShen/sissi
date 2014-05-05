package com.sissi.ucenter.relation.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * 默认订阅关系
 * 
 * @author kim 2014年2月13日
 */
public class DefaultRelation implements Relation, RosterRelation, MucRelation {

	private final static Map<String, Object> plus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final JID jid;

	private final String subscription;

	private boolean noneRole;

	private String role;

	private String affiliation;

	public DefaultRelation(JID jid, String subscription) {
		this.jid = jid;
		this.subscription = subscription;
	}

	/*
	 * JID.bare
	 * 
	 * @see com.sissi.ucenter.relation.Relation#jid()
	 */
	@Override
	public String jid() {
		return this.jid.asStringWithBare();
	}

	/*
	 * Null
	 * 
	 * @see com.sissi.ucenter.relation.Relation#name()
	 */
	@Override
	public String name() {
		return null;
	}

	@Override
	public boolean name(String name, boolean allowNull) {
		return false || allowNull;
	}

	@Override
	public String subscription() {
		return this.subscription;
	}

	public String role() {
		return this.noneRole ? null : this.role;
	}

	public DefaultRelation role(String role) {
		this.role = role;
		return this;
	}

	@Override
	public DefaultRelation role(String role, boolean force) {
		return this.role(role);
	}

	public DefaultRelation noneRole() {
		this.noneRole = true;
		return this;
	}

	public String affiliation() {
		return this.affiliation;
	}

	public DefaultRelation affiliation(String affiliation) {
		this.affiliation = affiliation;
		return this;
	}

	public DefaultRelation affiliation(String affiliation, boolean force) {
		return this.affiliation(affiliation);
	}

	public String resource() {
		return this.jid.resource();
	}

	public boolean in(String... subscriptions) {
		return false;
	}

	public boolean in(RosterSubscription... subscriptions) {
		return false;
	}

	public boolean activate() {
		return false;
	}

	public boolean outcast() {
		return ItemAffiliation.OUTCAST.equals(this.affiliation());
	}

	@Override
	public boolean ask() {
		return false;
	}

	@Override
	public Map<String, Object> plus() {
		return plus;
	}

	@Override
	public String[] groups() {
		return null;
	}

	@Override
	public <T extends Relation> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}
}