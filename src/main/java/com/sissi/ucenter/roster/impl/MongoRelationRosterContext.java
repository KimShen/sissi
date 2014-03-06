package com.sissi.ucenter.roster.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.impl.NoneRelation;
import com.sissi.ucenter.roster.RelationInductor;
import com.sissi.ucenter.roster.RelationRecover;
import com.sissi.ucenter.roster.RelationRoster;

/**
 * @author kim 2013-11-5
 */
public class MongoRelationRosterContext implements RelationContext, RelationRecover {

	private final String fieldAsk = "ask";

	private final String fieldSlave = "slave";

	private final String fieldMaster = "master";

	private final String fieldGroups = "groups";

	private final Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final Map<String, RelationUpdate> update = new HashMap<String, RelationUpdate>();

	private final DBObject filterMaster = BasicDBObjectBuilder.start(this.fieldMaster, 1).get();

	private final DBObject filterSlave = BasicDBObjectBuilder.start(this.fieldSlave, 1).get();

	private final DBObject entityInitMaster = BasicDBObjectBuilder.start(MongoConfig.FIELD_STATE, 0).get();

	private final DBObject entityInitSlave = BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ACTIVATE, false).add(MongoConfig.FIELD_STATE, 0).get();

	private final DBObject entityEstablishTo = BasicDBObjectBuilder.start("or", 1).get();

	private final DBObject entityEstablishFrom = BasicDBObjectBuilder.start("or", 2).get();

	private final DBObject entityBrokeTo = BasicDBObjectBuilder.start("and", 1).get();

	private final DBObject entityBrokeFrom = BasicDBObjectBuilder.start("and", 2).get();

	private final DBObject[] entityStates = new DBObject[] { BasicDBObjectBuilder.start(MongoConfig.FIELD_STATE, 1).get(), BasicDBObjectBuilder.start(MongoConfig.FIELD_STATE, 3).get() };

	private final String[] groups;

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	private RelationInductor relationInductor;

	public MongoRelationRosterContext(String group, MongoConfig config, JIDBuilder jidBuilder) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
		this.groups = new String[] { group };
		this.update.put(RosterSubscription.NONE.toString(), new RelationUpdate(this.entityBrokeTo, this.entityBrokeFrom));
		this.update.put(RosterSubscription.TO.toString(), new RelationUpdate(this.entityEstablishFrom, this.entityEstablishTo));
	}

	public void setRelationInductor(RelationInductor relationInductor) {
		this.relationInductor = relationInductor;
	}

	private DBObject buildQuery(String master, String slave) {
		return BasicDBObjectBuilder.start().add(this.fieldMaster, master).add(this.fieldSlave, slave).get();
	}

	private DBObject buildQueryWithRole(String role, String jid) {
		return BasicDBObjectBuilder.start().add(role, jid).add(MongoConfig.FIELD_ACTIVATE, true).get();
	}

	private DBObject buildQueryWithStates(String role, String jid, DBObject[] status) {
		DBObject query = this.buildQueryWithRole(role, jid);
		query.put("$or", status);
		return query;
	}

	@Override
	public MongoRelationRosterContext establish(JID from, Relation relation) {
		this.config.collection().update(this.buildQuery(from.asStringWithBare(), relation.getJID()), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(relation.plus()).add(MongoConfig.FIELD_NICK, relation.getName()).add(MongoConfig.FIELD_ACTIVATE, true).get()).add("$setOnInsert", this.entityInitMaster).get(), true, false, WriteConcern.SAFE);
		this.config.collection().update(this.buildQuery(relation.getJID(), from.asStringWithBare()), BasicDBObjectBuilder.start("$setOnInsert", this.entityInitSlave).get(), true, false, WriteConcern.SAFE);
		return this;
	}

	@Override
	public MongoRelationRosterContext update(JID from, JID to, String state) {
		this.config.collection().update(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()), BasicDBObjectBuilder.start().add("$unset", BasicDBObjectBuilder.start(this.fieldAsk, true).get()).add("$bit", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_STATE, this.update.get(state).getTo()).get()).get(), true, false, WriteConcern.SAFE);
		this.config.collection().update(this.buildQuery(to.asStringWithBare(), from.asStringWithBare()), BasicDBObjectBuilder.start("$bit", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_STATE, this.update.get(state).getFrom()).get()).get(), true, false, WriteConcern.SAFE);
		this.relationInductor.update(to, from);
		return this;
	}

	public MongoRelationRosterContext remove(JID from, JID to) {
		this.config.collection().remove(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()));
		this.config.collection().remove(this.buildQuery(to.asStringWithBare(), from.asStringWithBare()));
		this.relationInductor.remove(to, from);
		return this;
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().find(this.buildQueryWithRole(this.fieldMaster, from.asStringWithBare())));
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		DBObject db = this.config.collection().findOne(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()));
		return db != null ? new MongoRelationRoster(db, this.fieldSlave) : new NoneRelation(to, RosterSubscription.NONE.toString());
	}

	@Override
	public Set<JID> whoSubscribedMe(JID from) {
		return new MongoJIDGroup(this.config.collection().find(this.buildQueryWithStates(this.fieldSlave, from.asStringWithBare(), this.entityStates), this.filterMaster), this.fieldMaster);
	}

	public Set<JID> iSubscribedWho(JID from) {
		return new MongoJIDGroup(this.config.collection().find(this.buildQueryWithStates(this.fieldMaster, from.asStringWithBare(), this.entityStates), this.filterSlave), this.fieldSlave);
	}

	@Override
	public Set<Relation> recover(JID jid) {
		return new MongoRelations(this.config.collection().find(BasicDBObjectBuilder.start().add(this.fieldSlave, jid.asStringWithBare()).add(this.fieldAsk, true).get()), this.fieldMaster);
	}

	private class RelationUpdate {

		private final DBObject from;

		private final DBObject to;

		public RelationUpdate(DBObject from, DBObject to) {
			super();
			this.from = from;
			this.to = to;
		}

		public DBObject getFrom() {
			return this.from;
		}

		public DBObject getTo() {
			return this.to;
		}
	}

	private class MongoRelations extends HashSet<Relation> {

		private final static long serialVersionUID = 1L;

		public MongoRelations(DBCursor cursor) {
			this(cursor, MongoRelationRosterContext.this.fieldSlave);
		}

		public MongoRelations(DBCursor cursor, String fieldJID) {
			if (cursor == null) {
				return;
			}
			while (cursor.hasNext()) {
				this.add(new MongoRelationRoster(cursor.next(), fieldJID));
			}
			cursor.close();
		}
	}

	private class MongoRelationRoster implements RelationRoster {

		private final String jid;

		private final String name;

		private final boolean ask;

		private final boolean activate;

		private final Integer subscription;

		private final String[] groups;

		public MongoRelationRoster(DBObject db, String fieldJID) {
			super();
			this.jid = Extracter.asString(db, fieldJID);
			this.name = Extracter.asString(db, MongoConfig.FIELD_NICK);
			this.subscription = Extracter.asInt(db, MongoConfig.FIELD_STATE);
			this.activate = Extracter.asBoolean(db, MongoConfig.FIELD_ACTIVATE);
			this.ask = Extracter.asBoolean(db, MongoRelationRosterContext.this.fieldAsk);
			this.groups = Extracter.asStrings(db, MongoRelationRosterContext.this.fieldGroups);
		}

		public String getJID() {
			return this.jid;
		}

		public String getName() {
			return this.name;
		}

		@Override
		public boolean isAsk() {
			return this.ask;
		}

		public String[] asGroups() {
			return this.groups != null ? this.groups : MongoRelationRosterContext.this.groups;
		}

		public String getSubscription() {
			return RosterSubscription.toString(this.subscription);
		}

		public boolean in(String... subscriptions) {
			return RosterSubscription.parse(this.getSubscription()).in(subscriptions);
		}

		public boolean isActivate() {
			return this.activate;
		}

		public Map<String, Object> plus() {
			return MongoRelationRosterContext.this.fieldPlus;
		}

		@Override
		public <T extends Relation> T cast(Class<T> clazz) {
			return clazz.cast(this);
		}
	}

	private class MongoJIDGroup extends HashSet<JID> {

		private final static long serialVersionUID = 1L;

		public MongoJIDGroup(DBCursor cursor, String key) {
			if (cursor == null) {
				return;
			}
			while (cursor.hasNext()) {
				this.add(MongoRelationRosterContext.this.jidBuilder.build(cursor.next().get(key).toString()));
			}
			cursor.close();
		}
	}
}