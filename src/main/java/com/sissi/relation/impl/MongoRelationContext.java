package com.sissi.relation.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObjectBuilder;
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
	
	private final static DBObject FILTER_MASTER = BasicDBObjectBuilder.start("master", 1).get();

	private final static DBObject FILTER_SLAVE = BasicDBObjectBuilder.start("slave", 1).get();

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
		DBObject query = BasicDBObjectBuilder.start().add("master", from.asStringWithBare()).get();
		DBObject entity = BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(relation.plus()).add("slave", this.builder.build(relation.getJID()).asStringWithBare()).add("name", relation.getName()).add("state", relation.getSubscription()).get()).get();
		this.log.debug("Query is: " + query);
		this.log.debug("Entity is: " + entity);
		this.config.findCollection().update(query, entity, true, true);
	}

	@Override
	public void update(JID from, JID to, String state) {
		DBObject entity = BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add("state", state).get()).get();
		DBObject query = BasicDBObjectBuilder.start().add("master", from.asStringWithBare()).add("slave", to.asStringWithBare()).get();
		this.log.debug("Query is: " + query);
		this.log.debug("Entity is: " + entity);
		this.config.findCollection().update(query, entity);
	}

	public void remove(JID from, JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("master", from.asStringWithBare()).add("slave", to.asStringWithBare()).get();
		this.log.debug("Query is: " + query);
		this.config.findCollection().remove(query);
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		DBObject query = BasicDBObjectBuilder.start().add("master", from.asStringWithBare()).get();
		this.log.debug("Query is: " + query);
		return new MongoRelations(this.config.findCollection().find(query), this.config);
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("master", from.asStringWithBare()).add("slave", to.asStringWithBare()).get();
		this.log.debug("Query is: " + query);
		DBObject db = this.config.findCollection().findOne(query);
		return db != null ? new MongoRelation(db, this.config) : null;
	}

	@Override
	public Set<String> whoSubscribedMe(JID from) {
		DBObject query = BasicDBObjectBuilder.start().add("slave", from.asStringWithBare()).add("$or", Lists.newArrayList(BasicDBObjectBuilder.start().add("state", Roster.Subscription.TO.toString()).get(), BasicDBObjectBuilder.start().add("state", Roster.Subscription.BOTH.toString()).get())).get();
		this.log.debug("Query is: " + query);
		return new JIDs(this.config.findCollection().find(query, FILTER_MASTER), "master");
	}

	public Set<String> iSubscribedWho(JID from) {
		DBObject query =  BasicDBObjectBuilder.start("master", from.asStringWithBare()).add("$or", Lists.newArrayList(BasicDBObjectBuilder.start("state", Roster.Subscription.TO.toString()).get(), BasicDBObjectBuilder.start("state", Roster.Subscription.BOTH.toString()).get())).get();
		this.log.debug("Query is: " + query);
		return new JIDs(this.config.findCollection().find(query, FILTER_SLAVE), "slave");
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