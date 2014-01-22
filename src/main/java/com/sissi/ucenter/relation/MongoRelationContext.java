package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoCollection;
import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-5
 */
abstract class MongoRelationContext implements RelationContext {

	private final DBObject[] states = new DBObject[] { BasicDBObjectBuilder.start(MongoCollection.FIELD_STATE, 1).get(), BasicDBObjectBuilder.start(MongoCollection.FIELD_STATE, 3).get() };

	protected final Map<String, Object> plus = new HashMap<String, Object>();

	protected final Log log = LogFactory.getLog(this.getClass());

	protected final MongoConfig config;

	private final Map<String, RelationUpdate> update;

	public MongoRelationContext(MongoConfig config) {
		super();
		this.config = config;
		this.update = new HashMap<String, RelationUpdate>();
		this.update.put(RosterSubscription.TO.toString(), new RelationUpdate(MongoCollection.ENTITY_ESTABLISH_FROM, MongoCollection.ENTITY_ESTABLISH_TO));
		this.update.put(RosterSubscription.NONE.toString(), new RelationUpdate(MongoCollection.ENTITY_BROKE_TO, MongoCollection.ENTITY_BROKE_FROM));
	}

	private DBObject buildQuery(String master, String slave) {
		DBObject query = BasicDBObjectBuilder.start().add(MongoCollection.FIELD_MASTER, master).add(MongoCollection.FIELD_SLAVE, slave).get();
		this.log.debug("Query is: " + query);
		return query;
	}

	private DBObject buildStatesQuery(String role, JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add(role, jid.asStringWithBare()).get();
		query.put("$or", this.states);
		this.log.debug("Query is: " + query);
		return query;
	}

	private RelationContext update(DBObject query, DBObject entity) {
		this.log.debug("Query is: " + query);
		this.log.debug("Entity is: " + entity);
		this.config.collection().update(query, entity, true, false);
		return this;
	}

	@Override
	public RelationContext establish(JID from, Relation relation) {
		this.update(this.buildQuery(from.asStringWithBare(), relation.getJID()), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(relation.plus()).add(MongoCollection.FIELD_NICK, relation.getName()).get()).add("$setOnInsert", MongoCollection.ENTITY_STATE).get());
		this.update(this.buildQuery(relation.getJID(), from.asStringWithBare()), BasicDBObjectBuilder.start().add("$setOnInsert", MongoCollection.ENTITY_STATE).get());
		return this;
	}

	@Override
	public RelationContext update(JID from, JID to, String state) {
		this.update(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()), BasicDBObjectBuilder.start("$bit", BasicDBObjectBuilder.start().add(MongoCollection.FIELD_STATE, this.update.get(state).getTo()).get()).get());
		this.update(this.buildQuery(to.asStringWithBare(), from.asStringWithBare()), BasicDBObjectBuilder.start("$bit", BasicDBObjectBuilder.start().add(MongoCollection.FIELD_STATE, this.update.get(state).getFrom()).get()).get());
		return this;
	}

	public RelationContext remove(JID from, JID to) {
		this.update(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()), BasicDBObjectBuilder.start("$bit", BasicDBObjectBuilder.start().add(MongoCollection.FIELD_STATE, MongoCollection.ENTITY_BROKE_FROM).get()).get());
		this.update(this.buildQuery(to.asStringWithBare(), from.asStringWithBare()), BasicDBObjectBuilder.start("$bit", BasicDBObjectBuilder.start().add(MongoCollection.FIELD_STATE, MongoCollection.ENTITY_BROKE_TO).get()).get());
		return this;
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		DBObject query = BasicDBObjectBuilder.start(MongoCollection.FIELD_MASTER, from.asStringWithBare()).get();
		return new MongoRelations(this.config.collection().find(query));
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		DBObject db = this.config.collection().findOne(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()));
		return db != null ? this.build(db) : new NoneRelation(to);
	}

	@Override
	public Set<String> whoSubscribedMe(JID from) {
		return new JIDs(this.config.collection().find(this.buildStatesQuery(MongoCollection.FIELD_SLAVE, from), MongoCollection.FILTER_MASTER), MongoCollection.FIELD_MASTER);
	}

	public Set<String> iSubscribedWho(JID from) {
		return new JIDs(this.config.collection().find(this.buildStatesQuery(MongoCollection.FIELD_MASTER, from), MongoCollection.FILTER_SLAVE), MongoCollection.FIELD_SLAVE);
	}

	abstract protected Relation build(DBObject db);

	private class RelationUpdate {

		private DBObject from;

		private DBObject to;

		public RelationUpdate(DBObject from, DBObject to) {
			super();
			this.from = from;
			this.to = to;
		}

		public DBObject getFrom() {
			return from;
		}

		public DBObject getTo() {
			return to;
		}
	}

	private class NoneRelation implements Relation, RelationRoster {

		private JID jid;

		public NoneRelation(JID jid) {
			super();
			this.jid = jid;
		}

		@Override
		public String getJID() {
			return this.jid.asStringWithBare();
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public String getSubscription() {
			return RosterSubscription.NONE.toString();
		}

		@Override
		public Map<String, Object> plus() {
			return null;
		}

		@Override
		public String[] asGroups() {
			return null;
		}
	}

	private class JIDs extends HashSet<String> {

		private final static long serialVersionUID = 1L;

		public JIDs(DBCursor cursor, String key) {
			if (cursor == null) {
				return;
			}
			while (cursor.hasNext()) {
				this.add(cursor.next().get(key).toString());
			}
		}
	}

	private class MongoRelations extends HashSet<Relation> {

		private final static long serialVersionUID = 1L;

		public MongoRelations(DBCursor cursor) {
			if (cursor == null) {
				return;
			}
			while (cursor.hasNext()) {
				this.add(MongoRelationContext.this.build(cursor.next()));
			}
		}
	}
}