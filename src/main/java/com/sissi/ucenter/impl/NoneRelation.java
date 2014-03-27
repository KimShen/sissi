package com.sissi.ucenter.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.roster.RelationRoster;

/**
 * @author kim 2014年2月13日
 */
public class NoneRelation implements Relation, RelationRoster, RelationMuc {

	private final static Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final JID jid;

	private final String subscription;

	private boolean noneRole;

	private String role;

	private String affiliation;

	public NoneRelation(JID jid, String subscription) {
		this.jid = jid;
		this.subscription = subscription;
	}

	@Override
	public String jid() {
		return this.jid.asStringWithBare();
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public boolean name(String name, boolean allowNull) {
		return false || allowNull;
	}

	@Override
	public String getSubscription() {
		return this.subscription;
	}

	public String role() {
		return this.noneRole ? null : this.role;
	}

	public RelationMuc role(String role) {
		this.role = role;
		return this;
	}

	@Override
	public RelationMuc role(String role, boolean force) {
		return this.role(role);
	}

	public NoneRelation noneRole() {
		this.noneRole = true;
		return this;
	}

	public String affiliation() {
		return this.affiliation;
	}

	public RelationMuc affiliation(String affiliation) {
		this.affiliation = affiliation;
		return this;
	}

	public RelationMuc affiliation(String affiliation, boolean force) {
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
	public boolean isAsk() {
		return false;
	}

	@Override
	public Map<String, Object> plus() {
		return fieldPlus;
	}

	@Override
	public String[] asGroups() {
		return null;
	}

	@Override
	public <T extends Relation> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}
}