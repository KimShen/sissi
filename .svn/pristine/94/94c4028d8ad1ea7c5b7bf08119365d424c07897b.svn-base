package com.sissi.context.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.config.Config;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextPresence;
import com.sissi.context.JIDContextPresenceBuilder;
import com.sissi.util.MongoUtils;

/**
 * @author kim 2013-11-21
 */
public class MongoContextPresenceBuilder implements JIDContextPresenceBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final static String FIELD_PRIORITY = "priority";

	private final static DBObject DEFAULT_SORTER = new BasicDBObject(FIELD_PRIORITY, 1);

	private Config config;

	private MongoClient client;

	public MongoContextPresenceBuilder(Config config, MongoClient client) {
		super();
		this.config = config;
		this.client = client;
		MongoUtils.dropCollection(this.config, this.client);
	}

	@Override
	public JIDContextPresence build(JIDContext context) {
		return new MongoContextPresence(context);
	}

	private String get(JID jid, String key) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", jid.asStringWithBare()).add("resource", jid.getResource()).get();
		DBObject filter = BasicDBObjectBuilder.start(key, "1").get();
		this.log.debug("Query: " + query);
		this.log.debug("Filter: " + filter);
		DBCursor cursor = MongoUtils.findCollection(this.config, this.client).find(query, filter).sort(DEFAULT_SORTER).limit(1);
		return MongoUtils.getSecurityString(cursor.hasNext() ? cursor.next() : null, key);
	}

	private void set(JID jid, String key, String value) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", jid.asStringWithBare()).add("resource", jid.getResource()).get();
		DBObject entity = BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add(key, value).add("priority", jid.getPriority()).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		MongoUtils.findCollection(this.config, this.client).update(query, entity, true, false);
	}

	private void remove(JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", jid.asStringWithBare()).add("resource", jid.getResource()).get();
		this.log.debug("Query: " + query);
		MongoUtils.findCollection(this.config, this.client).remove(query);
	}

	private class MongoContextPresence implements JIDContextPresence {

		private JIDContext context;

		public MongoContextPresence(JIDContext context) {
			super();
			this.context = context;
		}

		@Override
		public String getTypeText() {
			return MongoContextPresenceBuilder.this.get(this.context.getJid(), "type");
		}

		@Override
		public String getShowText() {
			return MongoContextPresenceBuilder.this.get(this.context.getJid(), "show");
		}

		@Override
		public String getStatusText() {
			return MongoContextPresenceBuilder.this.get(this.context.getJid(), "status");
		}

		@Override
		public JIDContextPresence setTypeText(String type) {
			MongoContextPresenceBuilder.this.set(this.context.getJid(), "type", type);
			return this;
		}

		@Override
		public JIDContextPresence setShowText(String show) {
			MongoContextPresenceBuilder.this.set(this.context.getJid(), "show", show);
			return this;
		}

		@Override
		public JIDContextPresence setStatusText(String status) {
			MongoContextPresenceBuilder.this.set(this.context.getJid(), "status", status);
			return this;
		}

		public JIDContextPresence clear() {
			MongoContextPresenceBuilder.this.remove(this.context.getJid());
			return this;
		}
	}
}
