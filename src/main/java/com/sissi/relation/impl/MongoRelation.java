package com.sissi.relation.impl;

import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

/**
 * @author kim 2013-11-6
 */
public class MongoRelation extends RelationMapping {

	private ObjectId id;

	private String jid;

	private String name;

	private String group;

	private String subscription;

	public MongoRelation(DBObject db) {
		super();
		this.id = (ObjectId) db.get("_id");
		this.jid = this.secGet(db, "jid");
		this.name = this.secGet(db, "name");
		this.group = this.secGet(db, "group");
		this.subscription = this.secGet(db, "state");
	}

	private String secGet(DBObject db, String key) {
		Object value = db.get(key);
		return value != null ? value.toString() : null;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	@Override
	public Map<String, Object> toEntity() {
		Map<String, Object> entity = super.toEntity();
		entity.put("_id", this.id);
		return entity;
	}
}
