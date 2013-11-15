package com.sissi.relation.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.presence.Presence.Subscribe;
import com.sissi.relation.Relation;
import com.sissi.relation.Relation.State;
import com.sissi.relation.RelationContext;
import com.sissi.relation.impl.MongoRelation.MongoRelations;

/**
 * @author kim 2013-11-5
 */
public class MongoRelationContext implements RelationContext {

	private Log log = LogFactory.getLog(this.getClass());

	private String db;

	private String collection;

	private JIDBuilder jidBuilder;

	private MongoClient mongoClient;

	public MongoRelationContext(String db, String collection, MongoClient mongoClient, JIDBuilder jidBuilder) {
		super();
		this.db = db;
		this.collection = collection;
		this.mongoClient = mongoClient;
		this.jidBuilder = jidBuilder;
	}

	private DBCollection findCollection() {
		return this.mongoClient.getDB(this.db).getCollection(this.collection);
	}

	private Relation copyProperties(Relation from, Relation to) {
		BeanUtils.copyProperties(from, to);
		return to;
	}

	@Override
	public void subscribe(JID from, Relation relation) {
		Relation outRelation = this.prepareRelation(from, relation);
		BasicDBObject entity = new BasicDBObject(outRelation.toEntity());
		entity.put("from", from.asStringWithBare());
		this.log.info("Entity is: " + entity);
		this.findCollection().save(entity);
	}

	@Override
	public void subscribed(JID from, JID to) {
		DBObject entity = new BasicDBObject("$set", new BasicDBObject("state", Subscribe.SUBSCRIBED.toString()));
		DBObject query = new BasicDBObject();
		query.put("from", from.asStringWithBare());
		query.put("jid", to.asStringWithBare());
		this.updateRelation(entity, query);
	}

	@Override
	public void unsubscribed(JID from, JID to) {
		this.removeSubscribe(from, to);
	}

	private Relation prepareRelation(JID from, Relation relation) {
		Relation outRelation = this.ourRelation(from, this.jidBuilder.build(relation.getJID()));
		return outRelation != null ? this.copyProperties(relation, outRelation) : relation;
	}

	private void removeSubscribe(JID from, JID to) {
		DBObject query = new BasicDBObject();
		query.put("from", from.asStringWithBare());
		query.put("jid", to.asStringWithBare());
		DBObject entity = new BasicDBObject("$set", new BasicDBObject("state", Subscribe.REMOVE.toString()));
		this.log.info("Query is: " + query);
		this.log.info("Entity is: " + entity);
		this.findCollection().update(query, entity);
	}

	private void updateRelation(DBObject entity, DBObject query) {
		this.log.info("Query is: " + query);
		this.log.info("Entity is: " + entity);
		this.findCollection().update(query, entity);
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		DBObject query = new BasicDBObject("from", from.asStringWithBare());
		this.log.info("Query is: " + query);
		return new MongoRelations(this.mongoClient.getDB(this.db).getCollection(this.collection).find(query));
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		DBObject query = new BasicDBObject();
		query.put("from", from.asStringWithBare());
		query.put("jid", to.asStringWithBare());
		this.log.info("Query is: " + query);
		DBObject db = this.mongoClient.getDB(this.db).getCollection(this.collection).findOne(query);
		return db != null ? new MongoRelation(db) : null;
	}

	public Boolean weAreFriends(JID from, JID to) {
		Relation outRelation = this.ourRelation(to, from);
		return outRelation != null && outRelation.getSubscription().equals(State.SUBSCRIBED.toString());
	}

	@Override
	public Set<String> whoSubscribedMe(JID from) {
		BasicDBObject query = new BasicDBObject("jid", from.asStringWithBare());
		this.log.info("Query is: " + query);
		return new JIDs(this.findCollection().find(query, new BasicDBObject("from", 1)), "from");
	}

	public Set<String> iSubscribedWho(JID from) {
		BasicDBObject query = new BasicDBObject("from", from.asStringWithBare());
		this.log.info("Query is: " + query);
		return new JIDs(this.findCollection().find(query, new BasicDBObject("jid", 1)), "jid");
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

}