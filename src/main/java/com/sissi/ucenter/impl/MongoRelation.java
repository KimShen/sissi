package com.sissi.ucenter.impl;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-6
 */
public class MongoRelation implements RelationRoster {

	private final static Map<String, Object> PLUS = new HashMap<String, Object>();

	private final Boolean ban;
	
	private final String jid;
	
	private final String name;

	private final String group;

	private final String subscription;

	public MongoRelation(DBObject db, MongoConfig config) {
		super();
		this.ban = config.asBoolean(db, "ban");
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

	public String asGroup() {
		return group;
	}

	public String getSubscription() {
		return subscription;
	}
	
	public Boolean isBan(){
		return ban;
	}

	public String getJid() {
		return jid;
	}

	public Map<String, Object> plus() {
		return PLUS;
	}
}
