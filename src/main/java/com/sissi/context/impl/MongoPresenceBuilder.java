package com.sissi.context.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JIDContext;
import com.sissi.context.MyPresence;
import com.sissi.context.MyPresence.MyPresenceBuilder;

/**
 * @author kim 2013-11-21
 */
public class MongoPresenceBuilder implements MyPresenceBuilder {

	private final static String FIELD_PRIORITY = "priority";

	private final static DBObject DEFAULT_SORTER = new BasicDBObject(FIELD_PRIORITY, 1);

	private final Log log = LogFactory.getLog(this.getClass());

	private final MongoConfig config;

	public MongoPresenceBuilder(MongoConfig config) {
		super();
		this.config = config.dropCollection();
	}

	@Override
	public MyPresence build(JIDContext context) {
		return new MongoContextPresence(context);
	}

	private String get(JIDContext context, String key) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		DBObject filter = BasicDBObjectBuilder.start(key, "1").get();
		this.log.debug("Query: " + query);
		this.log.debug("Filter: " + filter);
		DBCursor cursor = this.config.findCollection().find(query, filter).sort(DEFAULT_SORTER).limit(1);
		return this.config.asString(cursor.hasNext() ? cursor.next() : null, key);
	}

	private void set(JIDContext context, String key, String value) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		DBObject upsert = BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add(key, value).add("priority", context.getPriority()).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Upsert: " + upsert);
		this.config.findCollection().update(query, upsert, true, false);
	}

	private void remove(JIDContext context) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).get();
		this.log.debug("Query: " + query);
		this.config.findCollection().remove(query);
	}

	private class MongoContextPresence implements MyPresence {

		private JIDContext context;

		public MongoContextPresence(JIDContext context) {
			super();
			this.context = context;
		}

		@Override
		public String getTypeText() {
			return MongoPresenceBuilder.this.get(this.context, "type");
		}

		@Override
		public String getShowText() {
			return MongoPresenceBuilder.this.get(this.context, "show");
		}

		@Override
		public String getStatusText() {
			return MongoPresenceBuilder.this.get(this.context, "status");
		}

		@Override
		public MyPresence setTypeText(String type) {
			MongoPresenceBuilder.this.set(this.context, "type", type);
			return this;
		}

		@Override
		public MyPresence setShowText(String show) {
			MongoPresenceBuilder.this.set(this.context, "show", show);
			return this;
		}

		@Override
		public MyPresence setStatusText(String status) {
			MongoPresenceBuilder.this.set(this.context, "status", status);
			return this;
		}

		public MyPresence clear() {
			MongoPresenceBuilder.this.remove(this.context);
			return this;
		}
	}
}
