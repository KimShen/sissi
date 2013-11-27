package com.sissi.addressing.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.addressing.Addressing;
import com.sissi.config.Config;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.impl.UserContexts;
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
		MongoUtils.dropCollection(this.config, this.client);
	}

	public void ban(JIDContext context) {
		if (this.isOnline(context.getJid())) {
			for (JIDContext current : this.find(context.getJid(), true)) {
				if (current.close()) {
					this.leave(current);
				}
			}
		}
	}

	@Override
	public void join(JIDContext context) {
		Long index = this.counter.incrementAndGet();
		DBObject entity = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).add(FIELD_PRIORITY, context.getJid().getPriority()).add("index", index).get();
		this.log.debug("Entity: " + entity);
		if (this.contexts.put(index, context) == null) {
			MongoUtils.findCollection(this.config, this.client).save(entity);
		} else {
			this.log.fatal("Index: " + index + " is leaked and this is fatal");
		}
	}

	@Override
	public void leave(JIDContext context) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).add(FIELD_PRIORITY, context.getJid().getPriority()).get();
		this.log.debug("Query: " + query);
		Long index = new Long(MongoUtils.findCollection(this.config, this.client).findOne(query, DEFAULT_FILTER).get("index").toString());
		if (this.contexts.remove(index) != null) {
			MongoUtils.findCollection(this.config, this.client).remove(query);
		} else {
			this.log.error("Index: " + index + " is leaked");
		}
	}

	@Override
	public UserContexts find(JID jid) {
		return this.find(jid, false);
	}

	@Override
	public JIDContext findOne(JID jid) {
		UserContexts contexts = this.find(jid);
		return !contexts.isEmpty() ? contexts.get(0) : this.offlineContextBuilder.build(jid, JIDContextParam.NOTHING);
	}

	private Boolean isOnline(JID jid) {
		return !this.find(jid, jid.getResource() != null, false).isEmpty();
	}

	private UserContexts find(JID jid, Boolean resource) {
		return this.find(jid, resource, true);
	}

	private UserContexts find(JID jid, Boolean resource, Boolean useOffline) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", jid.asStringWithBare()).get();
		if (resource) {
			query.put("resource", jid.getResource());
		}
		this.log.debug("Query: " + query);
		return new MongoUserContexts(jid, useOffline, MongoUtils.findCollection(this.config, this.client).find(query, DEFAULT_FILTER).sort(DEFAULT_SORTER));
	}

	private class MongoUserContexts extends UserContexts {

		private static final long serialVersionUID = 1L;

		private MongoUserContexts(JID jid, Boolean useOffline, DBCursor cursor) {
			while (cursor.hasNext()) {
				Long index = new Long(cursor.next().get("index").toString());
				JIDContext context = MongoHashAddressing.this.contexts.get(index);
				if (context != null) {
					this.add(context);
				} else {
					MongoHashAddressing.this.log.error("Index: " + index + " is leaked");
				}
			}
			this.ifOffline(jid, useOffline);
		}

		private void ifOffline(JID jid, Boolean useOffline) {
			if (useOffline && this.isEmpty()) {
				this.add(MongoHashAddressing.this.offlineContextBuilder.build(jid, JIDContextParam.NOTHING));
			}
		}
	}
}
