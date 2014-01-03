package com.sissi.addressing.impl;

import java.util.Date;
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
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.impl.JIDContexts;

/**
 * @author kim 2013-11-1
 */
public class MongoAddressing implements Addressing {

	private final Integer GC_THREAD = 1;

	private final String FIELD_INDEX = "index";

	private final String FIELD_CURRENT = "current";

	private final String FIELD_PRIORITY = "priority";

	private final DBObject DEFAULT_FILTER = BasicDBObjectBuilder.start(FIELD_INDEX, 1).get();

	private final DBObject DEFAULT_SORTER = BasicDBObjectBuilder.start().add(FIELD_PRIORITY, -1).add(FIELD_CURRENT, -1).get();

	private final JIDContextParam NOTHING = new NothingJIDContextParam();

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

	@Override
	public Addressing join(JIDContext context) {
		this.contexts.put(context.getIndex(), context);
		this.config.collection().save(this.buildQueryWithFullFields(context));
		return this;
	}

	public Addressing leave(JID jid) {
		for (JIDContext current : this.find(jid, true, false)) {
			this.leave(current);
		}
		return this;
	}

	@Override
	public Addressing leave(JIDContext context) {
		if (context.close()) {
			this.contexts.remove(context.getIndex());
			this.config.collection().remove(this.buildQueryWithFullFields(context));
		}
		return this;
	}

	public Integer others(JID jid) {
		return this.others(jid, false);
	}

	public Integer others(JID jid, Boolean usingResource) {
		return this.find(jid, usingResource, false).size();
	}

	@Override
	public JIDContexts find(JID jid) {
		return this.find(jid, false);
	}

	@Override
	public JIDContexts find(JID jid, Boolean usingResource) {
		return this.find(jid, usingResource, true);
	}

	@Override
	public JIDContext findOne(JID jid) {
		DBCursor entity = this.config.collection().find(this.buildQueryWithSmartResource(jid, false), DEFAULT_FILTER).sort(DEFAULT_SORTER).limit(1);
		return entity.hasNext() ? this.contexts.get(Long.class.cast(entity.next().get("index"))) : this.contextBuilder.build(jid, NOTHING);
	}

	public Addressing promote(JIDContext context) {
		this.config.collection().update(this.buildQueryWithSmartResource(context.getJid(), true), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(FIELD_PRIORITY, context.getPriority()).get()).get());
		return this;
	}

	@Override
	public Addressing activate(JIDContext context) {
		this.config.collection().update(this.buildQueryWithFullFields(context), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(FIELD_CURRENT, new Date().getTime()).get()).get());
		return this;
	}

	private JIDContexts find(JID jid, Boolean usingResource, Boolean usingOffline) {
		return new MongoUserContexts(jid, usingOffline, this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), DEFAULT_FILTER).sort(DEFAULT_SORTER));
	}

	private DBObject buildQueryWithFullFields(JIDContext context) {
		DBObject query = BasicDBObjectBuilder.start().add("jid", context.getJid().asStringWithBare()).add("resource", context.getJid().getResource()).add("index", context.getIndex()).add("address", context.getAddress().toString()).add(FIELD_PRIORITY, context.getPriority()).get();
		this.log.debug("Query: " + query);
		return query;
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
		return this.config.collection().findOne(BasicDBObjectBuilder.start().add("index", index).get(), DEFAULT_FILTER) != null;
	}

	private class MongoUserContexts extends JIDContexts {

		private final static long serialVersionUID = 1L;

		private MongoUserContexts(JID jid, Boolean usingOffline, DBCursor cursor) {
			while (cursor.hasNext()) {
				JIDContext context = MongoAddressing.this.contexts.get(Long.class.cast(cursor.next().get("index")));
				if (context != null) {
					this.add(context);
				}
			}
			this.usingOffline(jid, usingOffline);
		}

		private void usingOffline(JID jid, Boolean usingOffline) {
			if (usingOffline && this.isEmpty()) {
				this.add(MongoAddressing.this.contextBuilder.build(jid, NOTHING));
			}
		}
	}

	private class GC implements Runnable {

		private final Long sleep;

		public GC(Interval interval) {
			super();
			this.sleep = TimeUnit.MILLISECONDS.convert(interval.getInterval(), interval.getUnit());
		}

		@Override
		public void run() {
			while (true) {
				try {
					this.gc();
					Thread.sleep(this.sleep);
				} catch (Exception e) {
					if (MongoAddressing.this.log.isErrorEnabled()) {
						MongoAddressing.this.log.error(e.toString());
						e.printStackTrace();
					}
				}
			}
		}

		public void gc() {
			for (Long index : MongoAddressing.this.contexts.keySet()) {
				if (!MongoAddressing.this.exists(index)) {
					JIDContext leak = MongoAddressing.this.contexts.get(index);
					MongoAddressing.this.leave(leak);
					MongoAddressing.this.log.error("Find leak context: " + leak.getJid().asString());
				}
			}
		}
	}

	private class NothingJIDContextParam implements JIDContextParam {

		private NothingJIDContextParam() {
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			return null;
		}
	}
}
