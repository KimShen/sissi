package com.sissi.ucenter.relation;

import java.util.Map;

import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013年12月30日
 */
public class MongoRelationRosterContext extends MongoRelationContext {

	public MongoRelationRosterContext(MongoConfig config) {
		super(config);
	}

	@Override
	protected Relation build(DBObject db) {
		return new MongoRelationRoster(db);
	}

	private class MongoRelationRoster implements RelationRoster {

		private final String jid;

		private final String name;

		private final String group;

		private final String subscription;

		public MongoRelationRoster(DBObject db) {
			super();
			this.jid = MongoRelationRosterContext.super.config.asString(db, "slave");
			this.name = MongoRelationRosterContext.super.config.asString(db, "name");
			this.group = MongoRelationRosterContext.super.config.asString(db, "group");
			this.subscription = MongoRelationRosterContext.super.config.asString(db, "state");
		}

		public String getJID() {
			return this.jid;
		}

		public String getName() {
			return this.name;
		}

		public String asGroup() {
			return this.group;
		}

		public String getSubscription() {
			return this.subscription;
		}

		public Map<String, Object> plus() {
			return MongoRelationRosterContext.this.plus;
		}
	}
}
