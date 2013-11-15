package com.sissi.relation.impl;

import java.util.HashSet;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationMapping;

/**
 * @author kim 2013-11-6
 */
public class MongoRelation extends RelationMapping {

	private ObjectId id;

	private String jid;

	private String name;

	private String subscription;

	public MongoRelation(DBObject db) {
		super();
		this.id = (ObjectId) db.get("_id");
		this.jid = this.secGet(db, "jid");
		this.name = this.secGet(db, "name");
		this.subscription = this.secGet(db, "state");
	}

	private String secGet(DBObject db, String key) {
		Object value = db.get(key);
		return value != null ? value.toString() : null;
	}

	public String getJID() {
		return jid;
	}

	public String getName() {
		return name;
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

	@Override
	public Map<String, Object> toEntity() {
		Map<String, Object> entity = super.toEntity();
		entity.put("_id", this.id);
		return entity;
	}

	public static class MongoRelations extends HashSet<Relation> {

		private static final long serialVersionUID = 1L;

		public MongoRelations(DBCursor cursor) {
			if (cursor == null) {
				return;
			}
			while (cursor.hasNext()) {
				this.add(new MongoRelation(cursor.next()));
			}
		}
	}
}
