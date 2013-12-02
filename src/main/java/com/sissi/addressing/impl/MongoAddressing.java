package com.sissi.addressing.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.addressing.Addressing;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.JIDContextBuilder;
import com.sissi.context.JIDContext.JIDContextParam;
import com.sissi.context.impl.JIDContexts;
import com.sissi.thread.Interval;
import com.sissi.thread.Runner;

/**
 * @author kim 2013-11-1
 */
public class MongoAddressing implements Addressing {

	private final static Integer GC_THREAD = 1;

	private final static String FIELD_INDEX = "index";

	private final static String FIELD_PRIORITY = "priority";

	private final static DBObject DEFAULT_FILTER = BasicDBObjectBuilder.start(FIELD_INDEX, 1).get();

	private final static DBObject DEFAULT_SORTER = BasicDBObjectBuilder.start(FIELD_PRIORITY, 1).get();

	private final static JIDContextParam NOTHING = new NothingJIDContextParam();

	private final Log log = LogFactory.getLog(this.getClass());

	private final Map<Long, JIDContext> contexts = new ConcurrentHashMap<Long, JIDContext>();

	private final Interval gcInterval;

	private final MongoConfig config;

	private final JIDContextBuilder jidContextBuilder;

	public MongoAddressing(Runner runner, Interval gcInterval, MongoConfig config, JIDContextBuilder jidContextBuilder) {
		super();
		this.config = config.dropCollection();
		this.gcInterval = gcInterval;
		this.jidContextBuilder = jidContextBuilder;
		runner.executor(GC_THREAD, new GC());
	}

	public void ban(JIDContext context) {
		for (JIDContext current : this.find(context.getJid(), true, false)) {
			if (current.close()) {
				this.leave(current);
			} else {
				this.log.warn("Ban on " + current.getJid().asString() + " failed");
			}
		}
	}

	@Override
	public void join(JIDContext context) {
		this.contexts.put(context.getIndex(), context);
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).add(FIELD_PRIORITY, context.getPriority()).add("index", context.getIndex()).get();
		this.log.debug("Query: " + query);
		this.config.findCollection().save(query);
	}

	@Override
	public void leave(JIDContext context) {
		if (this.contexts.remove(context.getIndex()) != null) {
			DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).add(FIELD_PRIORITY, context.getPriority()).get();
			this.log.debug("Query: " + query);
			this.config.findCollection().remove(query);
		} else {
			this.log.error("Leave on " + context.getJid().asString() + " failed");
		}
	}

	@Override
	public JIDContexts find(JID jid) {
		return this.find(jid, false);
	}

	@Override
	public JIDContext findOne(JID jid) {
		DBObject query = this.buildQueryWithSmartResource(jid, false);
		this.log.debug("Query: " + query);
		DBObject entity = this.config.findCollection().findOne(query, DEFAULT_FILTER);
		return entity != null ? this.contexts.get(new Long(entity.get("index").toString())) : this.jidContextBuilder.build(jid, MongoAddressing.NOTHING);
	}

	private JIDContexts find(JID jid, Boolean usingResource) {
		return this.find(jid, usingResource, true);
	}

	private JIDContexts find(JID jid, Boolean usingResource, Boolean usingOffline) {
		DBObject query = this.buildQueryWithSmartResource(jid, true);
		this.log.debug("Query: " + query);
		return new MongoUserContexts(jid, usingOffline, this.config.findCollection().find(query, DEFAULT_FILTER).sort(DEFAULT_SORTER));
	}

	private DBObject buildQueryWithSmartResource(JID jid, Boolean usingResource) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", jid.asStringWithBare()).get();
		if (usingResource && jid.getResource() != null) {
			query.put("resource", jid.getResource());
		}
		this.log.debug("Query: " + query);
		return query;
	}

	private boolean exists(Long index) {
		DBObject query = BasicDBObjectBuilder.start().add("index", index).get();
		this.log.debug("Query: " + query);
		return this.config.findCollection().findOne(query, DEFAULT_FILTER) != null;
	}

	private class MongoUserContexts extends JIDContexts {

		private static final long serialVersionUID = 1L;

		private MongoUserContexts(JID jid, Boolean usingOffline, DBCursor cursor) {
			while (cursor.hasNext()) {
				Long index = new Long(cursor.next().get("index").toString());
				JIDContext context = MongoAddressing.this.contexts.get(index);
				if (context != null) {
					this.add(context);
				} else {
					MongoAddressing.this.log.warn("Index: " + index + " is leaked");
				}
			}
			this.usingOffline(jid, usingOffline);
		}

		private void usingOffline(JID jid, Boolean usingOffline) {
			if (usingOffline && this.isEmpty()) {
				MongoAddressing.this.log.debug("Using the offline context for " + jid.asString());
				this.add(MongoAddressing.this.jidContextBuilder.build(jid, MongoAddressing.NOTHING));
			}
		}
	}

	private class GC implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					this.gc();
					MongoAddressing.this.log.debug("Next gc after " + MongoAddressing.this.gcInterval.getInterval() + " / " + MongoAddressing.this.gcInterval.getUnit());
					Thread.sleep(TimeUnit.MICROSECONDS.convert(MongoAddressing.this.gcInterval.getInterval(), MongoAddressing.this.gcInterval.getUnit()));
				} catch (Exception e) {
					MongoAddressing.this.log.error(e);
				}
			}
		}

		private void gc() {
			for (Long index : MongoAddressing.this.contexts.keySet()) {
				if (!MongoAddressing.this.exists(index)) {
					JIDContext context = MongoAddressing.this.contexts.get(index);
					if (context != null) {
						context.close();
						MongoAddressing.this.log.error("Find leak context: " + context.getJid().asString());
					}
				}
			}
		}
	}

	public static class NothingJIDContextParam implements JIDContextParam {

		private NothingJIDContextParam() {
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			return null;
		}
	}
}
