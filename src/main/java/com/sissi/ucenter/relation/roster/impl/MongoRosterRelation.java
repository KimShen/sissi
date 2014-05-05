package com.sissi.ucenter.relation.roster.impl;

import java.util.Map;

import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.impl.MongoUtils;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * Mongo Item包装
 * 
 * @author kim 2014年4月22日
 */
public class MongoRosterRelation implements RosterRelation {

	private final String jid;

	private final String name;

	private final boolean ask;

	private final boolean activate;

	private final String[] groups;

	private final Integer subscription;

	private final Map<String, Object> plus;

	/**
	 * @param db
	 * @param role
	 * @param groups 默认Groups
	 * @param plus
	 */
	public MongoRosterRelation(DBObject db, String role, String[] groups, Map<String, Object> plus) {
		super();
		this.plus = plus;
		this.jid = MongoUtils.asString(db, role);
		this.ask = MongoUtils.asBoolean(db, Dictionary.FIELD_ACK);
		this.name = MongoUtils.asString(db, Dictionary.FIELD_NICK);
		this.activate = MongoUtils.asBoolean(db, Dictionary.FIELD_ACTIVATE);
		this.subscription = MongoUtils.asInt(db, Dictionary.FIELD_STATUS, 0);
		this.groups = MongoUtils.asStrings(db, Dictionary.FIELD_GROUPS, groups);
	}

	public String jid() {
		return this.jid;
	}

	public String name() {
		return this.name;
	}

	@Override
	public boolean ask() {
		return this.ask;
	}

	public boolean activate() {
		return this.activate;
	}

	public boolean in(String... subscriptions) {
		return RosterSubscription.parse(this.subscription()).in(subscriptions);
	}

	public String[] groups() {
		return this.groups;
	}

	public String subscription() {
		return RosterSubscription.toString(this.subscription);
	}

	public Map<String, Object> plus() {
		return this.plus;
	}

	@Override
	public <T extends Relation> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}
}
