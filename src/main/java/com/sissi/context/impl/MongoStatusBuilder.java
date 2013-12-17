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
import com.sissi.context.JIDContext.StatusBuilder;
import com.sissi.context.JIDContext.StatusClauses;

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

	private DBObject get(JIDContext context) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		this.log.debug("Query: " + query);
		DBCursor cursor = this.config.collection().find(query).sort(DEFAULT_SORTER).limit(1);
		return cursor.hasNext() ? cursor.next() : null;
	}

	private MongoStatusBuilder set(JIDContext context, String type, String show, String status, String avator) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		DBObject upsert = BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add("type", type).add("show", show).add("status", status).add("avator", avator).add("priority", context.getPriority()).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Upsert: " + upsert);
		this.config.collection().update(query, upsert, true, false);
		return this;
	}

	private MongoStatusBuilder remove(JIDContext context) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		this.log.debug("Query: " + query);
		this.config.collection().remove(query);
		return this;
	}

	private class MongoStatus implements Status {

		private JIDContext context;

		public MongoStatus(JIDContext context) {
			super();
			this.context = context;
		}

		public Status close() {
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
