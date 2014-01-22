package com.sissi.ucenter.relation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-5
 */
abstract class MongoRelationContext implements RelationContext {

	protected final String slave = "slave";

	protected final String master = "master";

	protected final String state = "state";

	protected final String nick = "nick";

	private final DBObject filterMaster = BasicDBObjectBuilder.start(master, 1).get();

	private final DBObject filterSlave = BasicDBObjectBuilder.start(slave, 1).get();

	private final DBObject entityState = BasicDBObjectBuilder.start(this.state, 0).get();

	private final DBObject entityEstablishTo = BasicDBObjectBuilder.start("or", 1).get();

	private final DBObject entityEstablishFrom = BasicDBObjectBuilder.start("or", 2).get();

	private final DBObject entityBrokeTo = BasicDBObjectBuilder.start("and", 1).get();

	private final DBObject entityBrokeFrom = BasicDBObjectBuilder.start("and", 2).get();

	private final DBObject[] entityStates = new DBObject[] { BasicDBObjectBuilder.start(this.state, 1).get(), BasicDBObjectBuilder.start(this.state, 3).get() };

	protected final Map<String, Object> plus = Collections.unmodifiableMap(new HashMap<String, Object>());

	protected final MongoConfig config;

	private final Map<String, RelationUpdate> update;

	public MongoRelationContext(MongoConfig config) {
		super();
		this.config = config;
		Map<String, RelationUpdate> update = new HashMap<String, RelationUpdate>();
		update.put(RosterSubscription.TO.toString(), new RelationUpdate(this.entityEstablishFrom, this.entityEstablishTo));
		update.put(RosterSubscription.NONE.toString(), new RelationUpdate(this.entityBrokeTo, this.entityBrokeFrom));
		this.update = Collections.unmodifiableMap(update);
	}

	private DBObject buildQuery(String master, String slave) {
		return BasicDBObjectBuilder.start().add(this.master, master).add(this.slave, slave).get();
	}

	private DBObject buildQueryLimitStates(String role, String jid) {
		DBObject query = BasicDBObjectBuilder.start().add(role, jid).get();
		query.put("$or", this.entityStates);
		return query;
	}

	@Override
	public RelationContext establish(JID from, Relation relation) {
		this.config.collection().update(this.buildQuery(from.asStringWithBare(), relation.getJID()), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(relation.plus()).add(this.nick, relation.getName()).get()).add("$setOnInsert", this.entityState).get(), true, false, WriteConcern.SAFE);
		this.config.collection().update(this.buildQuery(relation.getJID(), from.asStringWithBare()), BasicDBObjectBuilder.start("$setOnInsert", this.entityState).get(), true, false, WriteConcern.SAFE);
		return this;
	}

	@Override
	public RelationContext update(JID from, JID to, String state) {
		this.config.collection().update(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()), BasicDBObjectBuilder.start("$bit", BasicDBObjectBuilder.start().add(this.state, this.update.get(state).getTo()).get()).get(), true, false, WriteConcern.SAFE);
		this.config.collection().update(this.buildQuery(to.asStringWithBare(), from.asStringWithBare()), BasicDBObjectBuilder.start("$bit", BasicDBObjectBuilder.start().add(this.state, this.update.get(state).getFrom()).get()).get(), true, false, WriteConcern.SAFE);
		return this;
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().find(BasicDBObjectBuilder.start(this.master, from.asStringWithBare()).get()));
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		DBObject db = this.config.collection().findOne(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()));
		return db != null ? this.build(db) : new NoneRelation(to);
	}

	@Override
	public Set<String> whoSubscribedMe(JID from) {
		return new JIDs(this.config.collection().find(this.buildQueryLimitStates(this.slave, from.asStringWithBare()), this.filterMaster), this.master);
	}

	public Set<String> iSubscribedWho(JID from) {
		return new JIDs(this.config.collection().find(this.buildQueryLimitStates(this.master, from.asStringWithBare()), this.filterSlave), this.slave);
	}

	abstract protected Relation build(DBObject db);

	private class RelationUpdate {

		private final DBObject from;

		private final DBObject to;

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

		private final JID jid;

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