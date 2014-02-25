package com.sissi.context.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoProxyConfig;
import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.context.StatusBuilder;
import com.sissi.context.StatusClauses;

/**
 * @author kim 2013-11-21
 */
public class MongoStatusBuilder implements StatusBuilder {

	private final DBObject filter = BasicDBObjectBuilder.start().add(StatusClauses.KEY_AVATOR, 1).add(StatusClauses.KEY_SHOW, 1).add(StatusClauses.KEY_STATUS, 1).add(StatusClauses.KEY_TYPE, 1).add(StatusClauses.KEY_PRIORITY, 1).get();

	private final MongoConfig config;

	public MongoStatusBuilder(MongoConfig config) {
		super();
		this.config = config.clear();
	}

	@Override
	public Status build(JIDContext context) {
		return new MongoStatus(context);
	}

	private MongoStatusBuilder clear(JIDContext context) {
		this.config.collection().remove(this.buildQuery(context));
		return this;
	}

	private MongoStatusBuilder set(JIDContext context, String type, String show, String status, String avator) {
		this.config.collection().update(this.buildQuery(context), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add(StatusClauses.KEY_TYPE, type).add(StatusClauses.KEY_SHOW, show).add(StatusClauses.KEY_STATUS, status).add(StatusClauses.KEY_AVATOR, avator).add(MongoProxyConfig.FIELD_PRIORITY, context.priority()).get()).get());
		return this;
	}

	private StatusClauses get(JIDContext context) {
		return new MongoClauses(this.config.collection().findOne(this.buildQuery(context), this.filter));
	}

	private DBObject buildQuery(JIDContext context) {
		return BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_INDEX, context.index()).add(MongoProxyConfig.FIELD_JID, context.jid().asStringWithBare()).add(MongoProxyConfig.FIELD_RESOURCE, context.jid().resource()).get();
	}

	private class MongoStatus implements Status {

		private JIDContext context;

		public MongoStatus(JIDContext context) {
			super();
			this.context = context;
		}

		public MongoStatus clear() {
			MongoStatusBuilder.this.clear(this.context);
			// clear reference to avoid gc failed
			this.context = null;
			return this;
		}

		public Status clauses(StatusClauses clauses) {
			MongoStatusBuilder.this.set(this.context, clauses.find(StatusClauses.KEY_TYPE), clauses.find(StatusClauses.KEY_SHOW), clauses.find(StatusClauses.KEY_STATUS), clauses.find(StatusClauses.KEY_AVATOR));
			return this;
		}

		@Override
		public StatusClauses clauses() {
			return MongoStatusBuilder.this.get(this.context);
		}
	}

	private class MongoClauses implements StatusClauses {

		private final DBObject status;

		public MongoClauses(DBObject status) {
			super();
			this.status = status;
		}

		@Override
		public String find(String key) {
			return Extracter.asString(this.status, key);
		}
	}
}
