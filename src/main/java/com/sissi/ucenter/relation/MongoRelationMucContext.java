package com.sissi.ucenter.relation;

import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.MucContext;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2013年12月30日
 */
public class MongoRelationMucContext extends MongoRelationContext implements MucContext {

	public MongoRelationMucContext(MongoConfig config) {
		super(config);
	}

	public Set<Relation> myMembers(JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("slave", to.asStringWithBare()).get();
		super.log.debug("Query is: " + query);
		return new MongoRelations(super.config.collection().find(query), super.config);
	}

	@Override
	protected Relation build(DBObject db, MongoConfig config) {
		return new MongoMucRoster(db, config);
	}

	private class MongoMucRoster implements RelationMuc {

		private final String jid;

		private final String name;

		private final String room;

		private final String role;

		private final String member;

		private final String service;

		private final String affiliation;

		private final String subscription;

		public MongoMucRoster(DBObject db, MongoConfig config) {
			super();
			this.jid = config.asString(db, "slave");
			this.name = config.asString(db, "name");
			this.room = config.asString(db, RelationMuc.KEY_ROOM);
			this.role = config.asString(db, RelationMuc.KEY_ROLE);
			this.member = config.asString(db, RelationMuc.KEY_MEMBER);
			this.service = config.asString(db, RelationMuc.KEY_SERVICE);
			this.affiliation = config.asString(db, RelationMuc.KEY_AFFILICATION);
			this.subscription = null;
		}

		public String getJID() {
			return this.jid;
		}

		public String getName() {
			return this.name;
		}

		public String getSubscription() {
			return this.subscription;
		}

		@Override
		public String getRoom() {
			return this.room;
		}

		@Override
		public String getRole() {
			return this.role;
		}

		@Override
		public String getMember() {
			return this.member;
		}

		@Override
		public String getService() {
			return this.service;
		}

		@Override
		public String getAffiliation() {
			return this.affiliation;
		}

		@Override
		public MongoMucRoster set(String role, String affiliation) {
			return this;
		}

		public Map<String, Object> plus() {
			return MongoRelationMucContext.this.PLUS;
		}
	}
}
