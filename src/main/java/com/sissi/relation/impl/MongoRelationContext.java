package com.sissi.relation.impl;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.context.JID;
import com.sissi.context.user.User;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationContext;
import com.sissi.relation.State;

/**
 * @author kim 2013-11-5
 */
public class MongoRelationContext implements RelationContext {

	private Log log = LogFactory.getLog(this.getClass());

	private String dbName;

	private String clName;

	private MongoClient mongoClient;

	public MongoRelationContext(String dbName, String clName, MongoClient mongoClient) {
		super();
		this.dbName = dbName;
		this.clName = clName;
		this.mongoClient = mongoClient;
	}

	@Override
	public void subscribe(JID from, Relation relation) {
		Relation outRelation = this.prepareRelation(from, relation);
		BasicDBObject entity = new BasicDBObject(outRelation.toEntity());
		entity.put("from", from.asStringWithNaked());
		this.log.info("Entity is: " + entity);
		this.mongoClient.getDB(this.dbName).getCollection(this.clName).save(entity);
	}

	@Override
	public void subscribed(JID from, JID to) {
		DBObject entity = new BasicDBObject("$set", new BasicDBObject("state", "subscribed"));
		DBObject query = new BasicDBObject();
		query.put("from", from.asStringWithNaked());
		query.put("jid", to.asStringWithNaked());
		this.updateRelation(entity, query);
	}

	@Override
	public void unsubscribed(JID from, JID to) {
		this.remoteSubscribe(from, to);
	}

	private Relation prepareRelation(JID from, Relation relation) {
		Relation outRelation = this.ourRelation(from, new User(relation.getJid()));
		if (outRelation != null) {
			BeanUtils.copyProperties(relation, outRelation);
			return outRelation;
		} else {
			return relation;
		}
	}

	private void remoteSubscribe(JID from, JID to) {
		DBObject query = new BasicDBObject();
		query.put("from", from.asStringWithNaked());
		query.put("jid", to.asStringWithNaked());
		query.put("state", "subscribed");
		this.log.info("Query is: " + query);
		this.mongoClient.getDB(this.dbName).getCollection(this.clName).remove(query);
	}

	private void updateRelation(DBObject entity, DBObject query) {
		this.log.info("Query is: " + query);
		this.log.info("Entity is: " + entity);
		this.mongoClient.getDB(this.dbName).getCollection(this.clName).update(query, entity);
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		DBObject query = new BasicDBObject("from", from.asStringWithNaked());
		this.log.info("Query is: " + query);
		return new MongoRelations(this.mongoClient.getDB(this.dbName).getCollection(this.clName).find(query));
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		DBObject query = new BasicDBObject();
		query.put("from", from.asStringWithNaked());
		query.put("jid", to.asStringWithNaked());
		this.log.info("Query is: " + query);
		DBObject db = this.mongoClient.getDB(this.dbName).getCollection(this.clName).findOne(query);
		return db != null ? new MongoRelation(db) : null;
	}

	public Boolean weAreFriends(JID from, JID to) {
		Relation outRelation = this.ourRelation(to, from);
		return outRelation != null && outRelation.getSubscription().equals(State.SUBSCRIBED.toString());
	}

	@Override
	public Set<String> whoSubscribedMe(JID from) {
		BasicDBObject query = new BasicDBObject("jid", from.asStringWithNaked());
		this.log.info("Query is: " + query);
		return new JIDs(this.mongoClient.getDB(this.dbName).getCollection(this.clName).find(query, new BasicDBObject("from", 1)), "from");
	}

	public Set<String> iSubscribedWho(JID from) {
		BasicDBObject query = new BasicDBObject("from", from.asStringWithNaked());
		this.log.info("Query is: " + query);
		return new JIDs(this.mongoClient.getDB(this.dbName).getCollection(this.clName).find(query, new BasicDBObject("jid", 1)), "jid");
	}
}