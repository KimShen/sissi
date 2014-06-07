package com.sissi.ucenter.relation.roster.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.RelationContext;
import com.sissi.ucenter.relation.roster.RelationAck;
import com.sissi.ucenter.relation.roster.RelationCascade;

/**
 * @author kim 2014年4月22日
 */
public class MongoRelationRosterContext implements RelationContext, RelationAck {

	private final Log log = LogFactory.getLog(this.getClass());

	private final Map<String, RelationUpdate> update = new HashMap<String, RelationUpdate>();

	private final Map<String, Object> plus = Collections.unmodifiableMap(new HashMap<String, Object>());

	/**
	 * {"slave":1}
	 */
	private final DBObject filterSlave = BasicDBObjectBuilder.start(Dictionary.FIELD_SLAVE, 1).get();

	/**
	 * {"master":1}
	 */
	private final DBObject filterMaster = BasicDBObjectBuilder.start(Dictionary.FIELD_MASTER, 1).get();

	/**
	 * 初始化Master,{"status":0}
	 */
	private final DBObject initMaster = BasicDBObjectBuilder.start(Dictionary.FIELD_STATUS, 0).get();

	/**
	 * 初始化Salve,{"activate":false,"status":0}
	 */
	private final DBObject initSlave = BasicDBObjectBuilder.start().add(Dictionary.FIELD_ACTIVATE, false).add(Dictionary.FIELD_STATUS, 0).get();

	/**
	 * 建立订阅To,{"or":1}
	 */
	private final DBObject establishTo = BasicDBObjectBuilder.start("or", 1).get();

	/**
	 * 建立订阅From,{"or":2}
	 */
	private final DBObject establishFrom = BasicDBObjectBuilder.start("or", 2).get();

	/**
	 * 删除订阅To,{"and":1}
	 */
	private final DBObject brokeTo = BasicDBObjectBuilder.start("and", 1).get();

	/**
	 * 删除订阅From,{"and":1}
	 */
	private final DBObject brokeFrom = BasicDBObjectBuilder.start("and", 2).get();

	/**
	 * 有效的订阅关系,[{"status":1},{"status":3}]
	 */
	private final DBObject[] states = new DBObject[] { BasicDBObjectBuilder.start(Dictionary.FIELD_STATUS, 1).get(), BasicDBObjectBuilder.start(Dictionary.FIELD_STATUS, 3).get() };

	/**
	 * 默认Group
	 */
	private final String[] groups;

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	private final MongoOurRelation ourRelation;

	private final RelationCascade relationCascade;

	public MongoRelationRosterContext(String group, MongoConfig config, JIDBuilder jidBuilder, MongoOurRelation ourRelation, RelationCascade relationCascade) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
		this.ourRelation = ourRelation;
		this.relationCascade = relationCascade;
		this.groups = new String[] { group };
		this.update.put(RosterSubscription.NONE.toString(), new RelationUpdate(this.brokeTo, this.brokeFrom));
		this.update.put(RosterSubscription.TO.toString(), new RelationUpdate(this.establishFrom, this.establishTo));
	}

	/**
	 * {"master":Xxx,"slave":Xxx}
	 * 
	 * @param master
	 * @param slave
	 * @return
	 */
	private DBObject buildQuery(String master, String slave) {
		return this.ourRelation.buildQuery(master, slave);
	}

	/**
	 * {角色:jid,"activate":true}
	 * 
	 * @param role
	 * @param jid
	 * @return
	 */
	private DBObject buildQueryWithRole(String role, String jid) {
		return BasicDBObjectBuilder.start().add(role, jid).add(Dictionary.FIELD_ACTIVATE, true).get();
	}

	/**
	 * {角色:jid,"activate":true,"$or":有效的订阅关系,[{"status":1},{"status":3}]}
	 * 
	 * @param role
	 * @param jid
	 * @param status
	 * @return
	 */
	private DBObject buildQueryWithStates(String role, String jid, DBObject[] status) {
		DBObject query = this.buildQueryWithRole(role, jid);
		query.put("$or", status);
		return query;
	}

	@Override
	public MongoRelationRosterContext establish(JID from, Relation relation) {
		// {"$set":{(...relation.plus()...),"nick":relation.name(),"activate":true},"$setOnInsert":{"status":0}}
		// {"$setOnInsert":{"activate":false,"status":0}}
		if (MongoUtils.effect(this.config.collection().update(this.buildQuery(from.asStringWithBare(), relation.jid()), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(relation.plus()).add(Dictionary.FIELD_NICK, relation.name()).add(Dictionary.FIELD_ACTIVATE, true).get()).add("$setOnInsert", this.initMaster).get(), true, false, WriteConcern.SAFE)) && MongoUtils.effect(this.config.collection().update(this.buildQuery(relation.jid(), from.asStringWithBare()), BasicDBObjectBuilder.start("$setOnInsert", this.initSlave).get(), true, false, WriteConcern.SAFE))) {
			return this;
		}
		this.log.error("Establish warning: " + from.asStringWithBare() + " / " + relation.jid());
		return this;
	}

	@Override
	public MongoRelationRosterContext update(JID from, JID to, String state) {
		// {"$unset":{"ack":true},"$bit":{"status",this.update.Xxx}}
		// {"$bit":{"status",this.update.Xxx}}
		if (MongoUtils.effect(this.config.collection().update(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()), BasicDBObjectBuilder.start().add("$unset", BasicDBObjectBuilder.start(Dictionary.FIELD_ACK, true).get()).add("$bit", BasicDBObjectBuilder.start().add(Dictionary.FIELD_STATUS, this.update.get(state).getTo()).get()).get(), true, false, WriteConcern.SAFE)) && MongoUtils.effect(this.config.collection().update(this.buildQuery(to.asStringWithBare(), from.asStringWithBare()), BasicDBObjectBuilder.start("$bit", BasicDBObjectBuilder.start().add(Dictionary.FIELD_STATUS, this.update.get(state).getFrom()).get()).get(), true, false, WriteConcern.SAFE))) {
			this.relationCascade.update(to, from);
			return this;
		}
		this.log.error("Update warning: " + from.asStringWithBare() + " / " + to.asStringWithBare() + " / " + state);
		return this;
	}

	public MongoRelationRosterContext remove(JID from, JID to) {
		if (MongoUtils.effect(this.config.collection().remove(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()), WriteConcern.SAFE)) && MongoUtils.effect(this.config.collection().remove(this.buildQuery(to.asStringWithBare(), from.asStringWithBare()), WriteConcern.SAFE))) {
			this.relationCascade.remove(to, from);
		} else {
			this.log.error("Remove warning: " + from.asStringWithBare() + " / " + to.asStringWithBare());
		}
		return this;
	}

	/*
	 * {"master":jid.bare,"activate":true}
	 * 
	 * @see com.sissi.ucenter.relation.RelationContext#myRelations(com.sissi.context.JID)
	 */
	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().find(this.buildQueryWithRole(Dictionary.FIELD_MASTER, from.asStringWithBare())));
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		return this.ourRelation.ourRelation(from, to);
	}

	@Override
	public Set<JID> whoSubscribedMe(JID from) {
		return new MongoJIDGroup(this.config.collection().find(this.buildQueryWithStates(Dictionary.FIELD_SLAVE, from.asStringWithBare(), this.states), this.filterMaster), Dictionary.FIELD_MASTER);
	}

	public Set<JID> iSubscribedWho(JID from) {
		return new MongoJIDGroup(this.config.collection().find(this.buildQueryWithStates(Dictionary.FIELD_MASTER, from.asStringWithBare(), this.states), this.filterSlave), Dictionary.FIELD_SLAVE);
	}

	/*
	 * {"slave":jid.bare,"ack":true}
	 * 
	 * @see com.sissi.ucenter.relation.roster.RelationAck#ack(com.sissi.context.JID)
	 */
	@Override
	public Set<Relation> ack(JID jid) {
		return new MongoRelations(this.config.collection().find(BasicDBObjectBuilder.start().add(Dictionary.FIELD_SLAVE, jid.asStringWithBare()).add(Dictionary.FIELD_ACK, true).get()), Dictionary.FIELD_MASTER);
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

		private MongoRelations(DBCursor cursor) {
			this(cursor, Dictionary.FIELD_SLAVE);
		}

		/**
		 * @param cursor
		 * @param field
		 */
		private MongoRelations(DBCursor cursor, String field) {
			try (DBCursor iterator = cursor) {
				if (iterator == null) {
					return;
				}
				while (iterator.hasNext()) {
					this.add(new MongoRosterRelation(iterator.next(), field, MongoRelationRosterContext.this.groups, MongoRelationRosterContext.this.plus));
				}
			}
		}
	}

	private class MongoJIDGroup extends HashSet<JID> {

		private final static long serialVersionUID = 1L;

		private MongoJIDGroup(DBCursor cursor, String key) {
			try (DBCursor iterator = cursor) {
				if (iterator == null) {
					return;
				}
				while (iterator.hasNext()) {
					this.add(MongoRelationRosterContext.this.jidBuilder.build(iterator.next().get(key).toString()));
				}
			}
		}
	}
}