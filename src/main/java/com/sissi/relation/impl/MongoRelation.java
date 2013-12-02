package com.sissi.relation.impl;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.relation.RelationRoster;

/**
 * @author kim 2013-11-6
 */
public class MongoRelation implements RelationRoster {

	private final static Map<String, Object> PLUS = new HashMap<String, Object>();

	private String jid;

	private String name;

	private String group;

	private String subscription;

	public MongoRelation(DBObject db, MongoConfig config) {
		super();
		this.jid = config.asString(db, "slave");
		this.name = config.asString(db, "name");
		this.group = config.asString(db, "group");
		this.subscription = config.asString(db, "state");
	}

	public String getJID() {
		return jid;
	}

	public String getName() {
		return name;
	}

	public String getGp() {
		return group;
	}

	public String getSubscription() {
		return subscription;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public Map<String, Object> plus() {
		return PLUS;
	}
}
