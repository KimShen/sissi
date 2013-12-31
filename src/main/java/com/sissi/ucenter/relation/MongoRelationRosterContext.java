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
	protected Relation build(DBObject db, MongoConfig config) {
		return new MongoRelationRoster(db, config);
	}

	private class MongoRelationRoster implements RelationRoster {

		private final String jid;

		private final String name;

		private final String group;

		private final String subscription;

		public MongoRelationRoster(DBObject db, MongoConfig config) {
			super();
			this.jid = config.asString(db, "slave");
			this.name = config.asString(db, "name");
			this.group = config.asString(db, "group");
			this.subscription = config.asString(db, "state");
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
			return MongoRelationRosterContext.this.PLUS;
		}
	}
}
