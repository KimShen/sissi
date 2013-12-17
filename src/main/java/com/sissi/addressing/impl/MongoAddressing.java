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
import com.sissi.commons.Interval;
import com.sissi.commons.Runner;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.JIDContextBuilder;
import com.sissi.context.JIDContext.JIDContextParam;
import com.sissi.context.impl.JIDContexts;

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

	private final MongoConfig config;

	private final JIDContextBuilder contextBuilder;

	public MongoAddressing(Runner runner, Interval interval, MongoConfig config, JIDContextBuilder contextBuilder) {
		super();
		this.config = config.clear();
		this.contextBuilder = contextBuilder;
		runner.executor(GC_THREAD, new GC(interval));
	}

	public Addressing close(JID jid) {
		for (JIDContext current : this.find(jid, true, false)) {
			if (current.close()) {
				this.leave(current);
			}
		}
		return this;
	}

	@Override
	public Addressing join(JIDContext context) {
		this.contexts.put(context.getIndex(), context);
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).add(FIELD_PRIORITY, context.getPriority()).add("index", context.getIndex()).get();
		this.log.debug("Query: " + query);
		this.config.collection().save(query);
		return this;
	}

	@Override
	public Addressing leave(JIDContext context) {
		if (this.contexts.remove(context.getIndex()) != null) {
			DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).add(FIELD_PRIORITY, context.getPriority()).get();
			this.log.debug("Query: " + query);
			this.config.collection().remove(query);
		}
		return this;
	}

	@Override
	public JIDContexts find(JID jid) {
		return this.find(jid, false);
	}

	public JIDContexts find(JID jid, Boolean usingResource) {
		return this.find(jid, usingResource, true);
	}
	
	@Override
	public JIDContext findOne(JID jid) {
		DBObject query = this.buildQueryWithSmartResource(jid, false);
		this.log.debug("Query: " + query);
		DBObject entity = this.config.collection().findOne(query, DEFAULT_FILTER);
		return entity != null ? this.contexts.get(Long.class.cast(entity.get("index"))) : this.contextBuilder.build(jid, MongoAddressing.NOTHING);
	}

	private JIDContexts find(JID jid, Boolean usingResource, Boolean usingOffline) {
		DBObject query = this.buildQueryWithSmartResource(jid, usingResource);
		this.log.debug("Query: " + query);
		return new MongoUserContexts(jid, usingOffline, this.config.collection().find(query, DEFAULT_FILTER).sort(DEFAULT_SORTER));
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
		return this.config.collection().findOne(query, DEFAULT_FILTER) != null;
	}

	private class MongoUserContexts extends JIDContexts {

		private static final long serialVersionUID = 1L;

		private MongoUserContexts(JID jid, Boolean usingOffline, DBCursor cursor) {
			while (cursor.hasNext()) {
				Long index = Long.class.cast(cursor.next().get("index"));
				JIDContext context = MongoAddressing.this.contexts.get(index);
				if (context != null) {
					this.add(context);
				}
			}
			this.usingOffline(jid, usingOffline);
		}

		private void usingOffline(JID jid, Boolean usingOffline) {
			if (usingOffline && this.isEmpty()) {
				this.add(MongoAddressing.this.contextBuilder.build(jid, MongoAddressing.NOTHING));
			}
		}
	}

	private class GC implements Runnable {

		private final Interval interval;

		public GC(Interval interval) {
			super();
			this.interval = interval;
		}

		@Override
		public void run() {
			while (true) {
				try {
					this.gc();
					MongoAddressing.this.log.debug("Next gc after " + this.interval.getInterval() + " / " + interval.getUnit());
					Thread.sleep(TimeUnit.MICROSECONDS.convert(this.interval.getInterval(), this.interval.getUnit()));
				} catch (Exception e) {
					MongoAddressing.this.log.error(e);
				}
			}
		}

		public void gc() {
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

	private static class NothingJIDContextParam implements JIDContextParam {

		private NothingJIDContextParam() {
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			return null;
		}
	}
}
