package com.sissi.relation.impl;

<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;

import com.mongodb.DBObject;
import com.sissi.relation.RelationRoster;
import com.sissi.util.MongoUtils;
=======
import java.util.HashSet;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationMapping;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3

/**
 * @author kim 2013-11-6
 */
<<<<<<< HEAD
public class MongoRelation implements RelationRoster {

	private final static Map<String, Object> PLUS = new HashMap<String, Object>();
=======
public class MongoRelation extends RelationMapping {

	private ObjectId id;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3

	private String jid;

	private String name;

<<<<<<< HEAD
	private String group;

=======
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	private String subscription;

	public MongoRelation(DBObject db) {
		super();
<<<<<<< HEAD
		this.jid = MongoUtils.getSecurityString(db, "slave");
		this.name = MongoUtils.getSecurityString(db, "name");
		this.group = MongoUtils.getSecurityString(db, "group");
		this.subscription = MongoUtils.getSecurityString(db, "state");
=======
		this.id = (ObjectId) db.get("_id");
		this.jid = this.secGet(db, "jid");
		this.name = this.secGet(db, "name");
		this.subscription = this.secGet(db, "state");
	}

	private String secGet(DBObject db, String key) {
		Object value = db.get(key);
		return value != null ? value.toString() : null;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	}

	public String getJID() {
		return jid;
	}

	public String getName() {
		return name;
	}

<<<<<<< HEAD
	public String getGp() {
		return group;
	}

=======
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
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

<<<<<<< HEAD
	public Map<String, Object> plus() {
		return PLUS;
=======
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
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	}
}
