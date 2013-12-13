package com.sissi.context.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.Status;
import com.sissi.context.JIDContext.Status.StatusBuilder;

/**
 * @author kim 2013-11-21
 */
public class MongoStatusBuilder implements StatusBuilder {

	private final static DBObject DEFAULT_SORTER = new BasicDBObject("priority", 1);

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

	private String get(JIDContext context, String key) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		DBObject filter = BasicDBObjectBuilder.start(key, "1").get();
		this.log.debug("Query: " + query);
		this.log.debug("Filter: " + filter);
		DBCursor cursor = this.config.collection().find(query, filter).sort(DEFAULT_SORTER).limit(1);
		return this.config.asString(cursor.hasNext() ? cursor.next() : null, key);
	}

	private void set(JIDContext context, String key, String value) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		DBObject upsert = BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add(key, value).add("priority", context.getPriority()).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Upsert: " + upsert);
		this.config.collection().update(query, upsert, true, false);
	}

	private void remove(JIDContext context) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		this.log.debug("Query: " + query);
		this.config.collection().remove(query);
	}

	private class MongoStatus implements Status {

		private JIDContext context;

		public MongoStatus(JIDContext context) {
			super();
			this.context = context;
		}

		@Override
		public String getTypeAsText() {
			return MongoStatusBuilder.this.get(this.context, "type");
		}

		@Override
		public String getShowAsText() {
			return MongoStatusBuilder.this.get(this.context, "show");
		}

		@Override
		public String getStatusAsText() {
			return MongoStatusBuilder.this.get(this.context, "status");
		}

		@Override
		public String getAvatorAsText() {
			return MongoStatusBuilder.this.get(this.context, "avator");
		}

		@Override
		public Status asType(String type) {
			MongoStatusBuilder.this.set(this.context, "type", type);
			return this;
		}

		@Override
		public Status asShow(String show) {
			MongoStatusBuilder.this.set(this.context, "show", show);
			return this;
		}

		@Override
		public Status asStatus(String status) {
			MongoStatusBuilder.this.set(this.context, "status", status);
			return this;
		}

		@Override
		public Status asAvator(String avator) {
			MongoStatusBuilder.this.set(this.context, "avator", avator);
			return this;
		}

		public Status clear() {
			MongoStatusBuilder.this.remove(this.context);
			return this;
		}
	}
}
