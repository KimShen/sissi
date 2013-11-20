package com.sissi.relation.impl;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.DBObject;
import com.sissi.relation.RelationRoster;
import com.sissi.util.MongoUtils;

/**
 * @author kim 2013-11-6
 */
public class MongoRelation implements RelationRoster {

	private final static Map<String, Object> PLUS = new HashMap<String, Object>();

	private String jid;

	private String name;

	private String group;

	private String subscription;

	public MongoRelation(DBObject db) {
		super();
		this.jid = MongoUtils.getSecurityString(db, "slave");
		this.name = MongoUtils.getSecurityString(db, "name");
		this.group = MongoUtils.getSecurityString(db, "group");
		this.subscription = MongoUtils.getSecurityString(db, "state");
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
