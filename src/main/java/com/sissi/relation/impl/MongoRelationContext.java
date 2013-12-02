package com.sissi.relation.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JID.JIDBuilder;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-5
 */
public class MongoRelationContext implements RelationContext {

	private final Log log = LogFactory.getLog(this.getClass());

	private MongoConfig config;

	private JIDBuilder builder;

	public MongoRelationContext(MongoConfig config, JIDBuilder builder) {
		super();
		this.config = config;
		this.builder = builder;
	}

	@Override
	public void establish(JID from, Relation relation) {
		BasicDBObject query = new BasicDBObject("master", from.asStringWithBare());
		BasicDBObject entity = new BasicDBObject();
		BasicDBObject updated = new BasicDBObject();
		updated.put("slave", this.builder.build(relation.getJID()).asStringWithBare());
		updated.put("name", relation.getName());
		updated.put("state", relation.getSubscription());
		updated.putAll(relation.plus());
		entity.put("$set", updated);
		this.log.debug("Query is: " + query);
		this.log.debug("Entity is: " + entity);
		this.config.findCollection().update(query, entity, true, true);
	}

	@Override
	public void update(JID from, JID to, String state) {
		DBObject entity = new BasicDBObject("$set", new BasicDBObject("state", state));
		DBObject query = new BasicDBObject();
		query.put("master", from.asStringWithBare());
		query.put("slave", to.asStringWithBare());
		this.log.debug("Query is: " + query);
		this.log.debug("Entity is: " + entity);
		this.config.findCollection().update(query, entity);
	}

	public void remove(JID from, JID to) {
		DBObject query = new BasicDBObject();
		query.put("master", from.asStringWithBare());
		query.put("slave", to.asStringWithBare());
		this.log.debug("Query is: " + query);
		this.config.findCollection().remove(query);
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		DBObject query = new BasicDBObject("master", from.asStringWithBare());
		this.log.debug("Query is: " + query);
		return new MongoRelations(this.config.findCollection().find(query), this.config);
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		DBObject query = new BasicDBObject();
		query.put("master", from.asStringWithBare());
		query.put("slave", to.asStringWithBare());
		this.log.debug("Query is: " + query);
		DBObject db = this.config.findCollection().findOne(query);
		return db != null ? new MongoRelation(db, this.config) : null;
	}

	@Override
	public Set<String> whoSubscribedMe(JID from) {
		BasicDBObject query = new BasicDBObject("slave", from.asStringWithBare());
		query.put("$or", Lists.newArrayList(new BasicDBObject("state", Roster.Subscription.TO.toString()), new BasicDBObject("state", Roster.Subscription.BOTH.toString())));
		this.log.debug("Query is: " + query);
		return new JIDs(this.config.findCollection().find(query, new BasicDBObject("master", 1)), "master");
	}

	public Set<String> iSubscribedWho(JID from) {
		BasicDBObject query = new BasicDBObject("master", from.asStringWithBare());
		query.put("$or", Lists.newArrayList(new BasicDBObject("state", Roster.Subscription.TO.toString()), new BasicDBObject("state", Roster.Subscription.BOTH.toString())));
		this.log.debug("Query is: " + query);
		return new JIDs(this.config.findCollection().find(query, new BasicDBObject("slave", 1)), "slave");
	}

	private static class JIDs extends HashSet<String> {

		private static final long serialVersionUID = 1L;

		public JIDs(DBCursor cursor, String key) {
			if (cursor == null) {
				return;
			}
			while (cursor.hasNext()) {
				this.add(cursor.next().get(key).toString());
			}
		}
	}

	public static class MongoRelations extends HashSet<Relation> {

		private static final long serialVersionUID = 1L;

		public MongoRelations(DBCursor cursor, MongoConfig config) {
			if (cursor == null) {
				return;
			}
			while (cursor.hasNext()) {
				this.add(new MongoRelation(cursor.next(), config));
			}
		}
	}
}