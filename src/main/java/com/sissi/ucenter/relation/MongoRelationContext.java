package com.sissi.ucenter.relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

	protected final Map<String, Object> plus = new HashMap<String, Object>();

	protected final Log log = LogFactory.getLog(this.getClass());

	protected final MongoConfig config;

	private final List<DBObject> states;

	public MongoRelationContext(MongoConfig config) {
		super();
		this.config = config;
		states = new ArrayList<DBObject>();
		states.add(BasicDBObjectBuilder.start().add(MongoCollection.FIELD_STATE, RosterSubscription.TO.toString()).get());
		states.add(BasicDBObjectBuilder.start().add(MongoCollection.FIELD_STATE, RosterSubscription.BOTH.toString()).get());
	}

	private DBObject buildQuery(String master, String slave) {
		DBObject query = BasicDBObjectBuilder.start().add(MongoCollection.FIELD_MASTER, master).add(MongoCollection.FIELD_SLAVE, slave).get();
		this.log.debug("Query is: " + query);
		return query;
	}

	private DBObject buildStatesQuery(String role, JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add(role, jid.asStringWithBare()).add("$or", states).get();
		this.log.debug("Query is: " + query);
		return query;
	}

	@Override
	public RelationContext establish(JID from, Relation relation) {
		DBObject entity = BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(relation.plus()).add("name", relation.getName()).get()).get();
		this.log.debug("Entity is: " + entity);
		this.config.collection().update(this.buildQuery(from.asStringWithBare(), relation.getJID()), entity, true, true);
		return this;
	}

	@Override
	public RelationContext update(JID from, JID to, String state) {
		DBObject entity = BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add(MongoCollection.FIELD_STATE, state).get()).get();
		this.log.debug("Entity is: " + entity);
		this.config.collection().update(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()), entity);
		return this;
	}

	public RelationContext remove(JID from, JID to) {
		this.config.collection().remove(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()));
		return this;
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().find(this.buildStatesQuery(MongoCollection.FIELD_MASTER, from)));
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

	protected class MongoRelations extends HashSet<Relation> {

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