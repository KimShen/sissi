package com.sissi.context.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoCollection;
import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.context.StatusBuilder;
import com.sissi.context.StatusClauses;

/**
 * @author kim 2013-11-21
 */
public class MongoStatusBuilder implements StatusBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final MongoConfig config;

	public MongoStatusBuilder(MongoConfig config) {
		super();
		this.config = config.clear();
	}

	@Override
	public Status build(JIDContext context) {
		return new MongoStatus(context);
	}

	private MongoStatusBuilder remove(JIDContext context) {
		this.config.collection().remove(this.buildQuery(context));
		return this;
	}

	private DBObject get(JIDContext context) {
		DBCursor cursor = this.config.collection().find(this.buildQuery(context)).sort(MongoCollection.DEFAULT_SORTER).limit(1);
		return cursor.hasNext() ? cursor.next() : null;
	}

	private MongoStatusBuilder set(JIDContext context, String type, String show, String status, String avator) {
		this.config.collection().update(this.buildQuery(context), this.buildUpsert(context, type, show, status, avator), true, false);
		return this;
	}

	private DBObject buildQuery(JIDContext context) {
		DBObject query = BasicDBObjectBuilder.start().add(MongoCollection.FIELD_JID, context.getJid().asStringWithBare()).add(MongoCollection.FIELD_RESOURCE, context.getJid().getResource()).get();
		this.log.debug("Query: " + query);
		return query;
	}

	private DBObject buildUpsert(JIDContext context, String type, String show, String status, String avator) {
		DBObject upsert = BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add("type", type).add("show", show).add("status", status).add("avator", avator).add(MongoCollection.FIELD_PRIORITY, context.getPriority()).get()).get();
		this.log.debug("Upsert: " + upsert);
		return upsert;
	}

	private class MongoStatus implements Status {

		private JIDContext context;

		public MongoStatus(JIDContext context) {
			super();
			this.context = context;
		}

		public Status clear() {
			MongoStatusBuilder.this.remove(this.context);
			this.context = null;
			return this;
		}

		@Override
		public Status setStatus(String type, String show, String status, String avator) {
			MongoStatusBuilder.this.set(this.context, type, show, status, avator);
			return this;
		}

		@Override
		public StatusClauses getStatus() {
			return new MongoClauses(MongoStatusBuilder.this.get(this.context));
		}
	}

	private class MongoClauses implements StatusClauses {

		private DBObject status;

		public MongoClauses(DBObject status) {
			super();
			this.status = status;
		}

		@Override
		public String find(String key) {
			return MongoStatusBuilder.this.config.asString(this.status, key);
		}
	}
}
