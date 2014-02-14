package com.sissi.ucenter.relation;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoProxyConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年2月11日
 */
public class MongoRelationMucContext implements RelationContext {

	private final String fieldRoom = "room";

	private final String fieldGroup = "group";

	private final String fieldCreator = "creator";

	private final String fieldActivate = "activate";

	private final Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final byte[] subscription = new BitSet().toByteArray();

	private final String subscriptionAsString = new String(this.subscription);

	private final DBObject entityInit = BasicDBObjectBuilder.start().add(this.fieldActivate, false).get();

	private final DBObject filterGroup = BasicDBObjectBuilder.start().add(this.fieldGroup, 1).get();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoRelationMucContext(MongoConfig config, JIDBuilder jidBuilder) throws Exception {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
	}

	private DBObject buildQuery(String jid) {
		return BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_JID, jid).get();
	}

	@Override
	public MongoRelationMucContext establish(JID from, Relation relation) {
		this.config.collection().update(this.buildQuery(relation.getJID()), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(relation.plus()).add(this.fieldCreator, from.asStringWithBare()).get()).add("$addToSet", BasicDBObjectBuilder.start(this.fieldGroup, BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_JID, from.asStringWithBare()).add(MongoProxyConfig.FIELD_NICK, relation.getName()).add(MongoProxyConfig.FIELD_STATE, this.subscription).get()).get()).add("$setOnInsert", this.entityInit).get(), true, false, WriteConcern.SAFE);
		return this;
	}

	@Override
	public MongoRelationMucContext update(JID from, JID to, String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MongoRelationMucContext remove(JID from, JID to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		return new NoneRelation(to, this.subscriptionAsString);
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().findOne(this.buildQuery(from.asStringWithBare()), this.filterGroup));
	}

	@Override
	public Set<JID> whoSubscribedMe(JID from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<JID> iSubscribedWho(JID from) {
		// TODO Auto-generated method stub
		return null;
	}

	private class MongoRelations extends HashSet<Relation> {

		private final static long serialVersionUID = 1L;

		public MongoRelations(DBObject db) {
			List<?> groups = List.class.cast(db.get(MongoRelationMucContext.this.fieldGroup));
			for (Object group : groups) {
				super.add(new MongoRelationMuc(DBObject.class.cast(group)));
			}
		}
	}

	private class MongoRelationMuc extends BitSetRelationMuc {

		private final String jid;

		private final String name;

		private final String room;

		private final Boolean activate;

		public MongoRelationMuc(DBObject db) {
			super(MongoRelationMucContext.this.config.asBytes(db, MongoProxyConfig.FIELD_STATE));
			this.jid = MongoRelationMucContext.this.config.asString(db, MongoProxyConfig.FIELD_JID);
			this.name = MongoRelationMucContext.this.config.asString(db, MongoProxyConfig.FIELD_NICK);
			this.room = MongoRelationMucContext.this.config.asString(db, MongoRelationMucContext.this.fieldRoom);
			this.activate = MongoRelationMucContext.this.config.asBoolean(db, MongoRelationMucContext.this.fieldActivate);
		}

		public String getJID() {
			return this.jid;
		}

		public String getRoom() {
			return this.room;
		}

		public String getName() {
			return this.name;
		}

		public boolean in(String... subscriptions) {
			return RosterSubscription.parse(this.getSubscription()).in(subscriptions);
		}

		public boolean isActivate() {
			return this.activate;
		}

		public Map<String, Object> plus() {
			return MongoRelationMucContext.this.fieldPlus;
		}
	}
}
