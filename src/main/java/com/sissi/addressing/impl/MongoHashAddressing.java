package com.sissi.addressing.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.addressing.Addressing;
import com.sissi.config.Config;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.util.MongoUtils;

/**
 * @author kim 2013-11-1
 */
public class MongoHashAddressing implements Addressing {

	private final static String FIELD_INDEX = "index";

	private final static String FIELD_PRIORITY = "priority";

	private final static DBObject DEFAULT_FILTER = new BasicDBObject(FIELD_INDEX, 1);

	private final static DBObject DEFAULT_SORTER = new BasicDBObject(FIELD_PRIORITY, 1);

	private final Log log = LogFactory.getLog(this.getClass());

	private final AtomicLong counter = new AtomicLong();

	private final Map<Long, JIDContext> contexts = new ConcurrentHashMap<Long, JIDContext>();

	private Config config;

	private MongoClient client;

	private JIDContextBuilder offlineContextBuilder;

	public MongoHashAddressing(Config config, MongoClient client, JIDContextBuilder offlineContextBuilder) {
		super();
		this.config = config;
		this.client = client;
		this.offlineContextBuilder = offlineContextBuilder;
		this.prepareDBAndInsureIndex();
	}

	/**
	 * Rebuild MongoDB collection, drop and ensureIndex
	 */
	private void prepareDBAndInsureIndex() {
		MongoUtils.findCollection(this.config, this.client).drop();
	}

	public void ban(JIDContext context) {
		// Close network and do other operation if context was onlined
		if (this.isOnline(context.getJid())) {
			for (JIDContext current : this.find(context.getJid(), true)) {
				// first close network then remove from DB
				// If network cannot be closed and throw exception, we still can
				// find context from DB to avoid leak
				if (current.close()) {
					this.leave(current);
				}
			}
		}
	}

	@Override
	public void join(JIDContext context) {
		// Context can bind with def index, but should use ban for security
		// memory manage
		DBObject entity = new BasicDBObject();
		entity.put("jid", context.getJid().asStringWithBare());
		entity.put("resource", context.getJid().getResource());
		entity.put(FIELD_PRIORITY, context.getJid().getPriority());
		Long index = this.counter.incrementAndGet();
		entity.put("index", index);
		if (this.contexts.put(index, context) == null) {
			this.log.debug("Entity: " + entity);
			MongoUtils.findCollection(this.config, this.client).save(entity);
		} else {
			this.log.fatal("Index: " + index + " is leaked and this is fatal");
		}
	}

	@Override
	public void leave(JIDContext context) {
		DBObject query = new BasicDBObject();
		query.put("jid", context.getJid().asStringWithBare());
		query.put("resource", context.getJid().getResource());
		query.put(FIELD_PRIORITY, context.getJid().getPriority());
		this.log.debug("Query: " + query);
		// Like ban as well
		Long index = new Long(MongoUtils.findCollection(this.config, this.client).findOne(query, DEFAULT_FILTER).get("index").toString());
		if (this.contexts.remove(index) != null) {
			MongoUtils.findCollection(this.config, this.client).remove(query);
		} else {
			this.log.error("Index: " + index + " is leaked");
		}
	}

	@Override
	public List<JIDContext> find(JID jid) {
		return this.find(jid, false);
	}

	private List<JIDContext> find(JID jid, Boolean resource) {
		DBObject query = new BasicDBObject();
		query.put("jid", jid.asStringWithBare());
		if (resource) {
			query.put("resource", jid.getResource());
		}
		this.log.debug("Query: " + query);
		return new JIDContexts(jid, MongoUtils.findCollection(this.config, this.client).find(query, DEFAULT_FILTER).sort(DEFAULT_SORTER));
	}

	@Override
	public JIDContext findOne(JID jid) {
		List<JIDContext> contexts = this.find(jid);
		return !contexts.isEmpty() ? contexts.get(0) : this.offlineContextBuilder.build(jid, JIDContextParam.NOTHING);
	}

	@Override
	public Boolean isOnline(JID jid) {
		DBObject query = new BasicDBObject();
		query.put("jid", jid.asStringWithBare());
		if (jid.getResource() != null) {
			query.put("resource", jid.getResource());
		}
		this.log.debug("Query: " + query);
		return MongoUtils.findCollection(this.config, this.client).count(query) != 0;

	}

	private class JIDContexts extends ArrayList<JIDContext> {

		private static final long serialVersionUID = 1L;

		private JIDContexts(JID jid, DBCursor cursor) {
			while (cursor.hasNext()) {
				Long index = new Long(cursor.next().get("index").toString());
				JIDContext context = MongoHashAddressing.this.contexts.get(index);
				if (context != null) {
					this.add(context);
				} else {
					MongoHashAddressing.this.log.error("Index: " + index + " is leaked");
				}
			}
			this.ifOffline(jid);
		}

		private void ifOffline(JID jid) {
			if (this.isEmpty()) {
				this.add(MongoHashAddressing.this.offlineContextBuilder.build(jid, JIDContextParam.NOTHING));
			}
		}
	}
}
