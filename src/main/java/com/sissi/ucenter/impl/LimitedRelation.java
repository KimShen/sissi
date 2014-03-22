package com.sissi.ucenter.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.roster.RelationRoster;

/**
 * @author kim 2014年2月13日
 */
public class LimitedRelation implements Relation, RelationRoster, RelationMuc {

	private final static Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final static String zero = "0";

	private final JID jid;

	private final String affiliation;

	private final String subscription;

	private boolean noneRole;

	private LimitedRelation(JID jid, String subscription, ItemAffiliation affiliation) {
		super();
		this.jid = jid;
		this.subscription = subscription;
		this.affiliation = affiliation.toString();
	}

	public LimitedRelation(JID jid, String subscription) {
		this(jid, subscription, ItemAffiliation.NONE);
	}

	public LimitedRelation(JID jid, ItemAffiliation affiliation) {
		this(jid, zero, affiliation);
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
		return this.noneRole ? null : ItemRole.VISITOR.toString();
	}

	public RelationMuc role(String role) {
		return this;
	}

	public LimitedRelation noneRole() {
		this.noneRole = true;
		return this;
	}

	public String affiliation() {
		return this.affiliation;
	}

	public RelationMuc affiliation(String affiliation) {
		return this;
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