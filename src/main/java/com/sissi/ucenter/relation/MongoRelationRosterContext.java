package com.sissi.ucenter.relation;

import java.util.Map;

import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013年12月30日
 */
public class MongoRelationRosterContext extends MongoRelationContext {

	private final String groups = "groups";

	public MongoRelationRosterContext(MongoConfig config, JIDBuilder jidBuilder) {
		super(config, jidBuilder);
	}

	@Override
	protected Relation build(DBObject db) {
		return new MongoRelationRoster(db);
	}

	private class MongoRelationRoster implements RelationRoster {

		private final String jid;

		private final String name;

		private final Boolean ask;

		private final Boolean activate;

		private final Integer subscription;

		private final String[] groups;

		public MongoRelationRoster(DBObject db) {
			super();
			this.jid = MongoRelationRosterContext.super.config.asString(db, MongoRelationRosterContext.super.fieldSlave);
			this.name = MongoRelationRosterContext.super.config.asString(db, MongoRelationRosterContext.super.fieldNick);
			this.ask = MongoRelationRosterContext.super.config.asBoolean(db, MongoRelationRosterContext.super.fieldAsk);
			this.activate = MongoRelationRosterContext.super.config.asBoolean(db, MongoRelationRosterContext.super.fieldActivate);
			this.subscription = MongoRelationRosterContext.super.config.asInteger(db, MongoRelationRosterContext.super.fieldState);
			this.groups = MongoRelationRosterContext.super.config.asStrings(db, MongoRelationRosterContext.this.groups);
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
			return this.groups;
		}

		public String getSubscription() {
			return RosterSubscription.toString(this.subscription);
		}

		public boolean in(String... subscriptions) {
			return RosterSubscription.parse(this.getSubscription()).in(subscriptions);
		}

		public boolean in(RosterSubscription... subscriptions) {
			return RosterSubscription.parse(this.getSubscription()).in(subscriptions);
		}

		public boolean isActivate() {
			return this.activate;
		}

		public Map<String, Object> plus() {
			return MongoRelationRosterContext.super.fieldPlus;
		}
	}
}
