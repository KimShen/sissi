package com.sissi.addressing.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.addressing.Addressing;
import com.sissi.commons.Interval;
import com.sissi.commons.Runner;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoCollection;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.impl.JIDContexts;
import com.sissi.gc.GC;
import com.sissi.resource.ResourceMonitor;

/**
 * @author kim 2013-11-1
 */
public class MongoAddressing implements Addressing {

	private final Integer gcThreads = 1;

	private final Log log = LogFactory.getLog(this.getClass());

	private final JIDContextParam nothing = new NothingJIDContextParam();

	private final Map<Long, JIDContext> contexts = new ConcurrentHashMap<Long, JIDContext>();

	private final MongoConfig config;

	private final JIDContextBuilder offlineContextBuilder;

	public MongoAddressing(Runner runner, Interval interval, MongoConfig config, ResourceMonitor resourceMonitor, JIDContextBuilder offlineContextBuilder) {
		super();
		this.config = config.clear();
		this.offlineContextBuilder = offlineContextBuilder;
		runner.executor(this.gcThreads, new JIDContextGC(interval, resourceMonitor));
	}

	@Override
	public Addressing join(JIDContext context) {
		this.contexts.put(context.getIndex(), context);
		this.config.collection().save(this.buildQueryWithNecessaryFields(context));
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
			this.config.collection().remove(this.buildQueryWithNecessaryFields(this.contexts.remove(context.getIndex())));
		}
		return this;
	}

	public Integer others(JID jid) {
		return this.others(jid, false);
	}

	public Integer others(JID jid, Boolean usingResource) {
		return this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource)).count();
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
		return this.findOne(jid, false);
	}

	public JIDContext findOne(JID jid, Boolean usingResource) {
		DBCursor entity = this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), MongoCollection.FILTER_INDEX).sort(MongoCollection.DEFAULT_SORTER).limit(1);
		return entity.hasNext() ? this.contexts.get(Long.class.cast(entity.next().get(MongoCollection.FIELD_INDEX))) : this.offlineContextBuilder.build(jid, this.nothing);
	}

	public Addressing priority(JIDContext context) {
		this.config.collection().update(this.buildQueryWithSmartResource(context.getJid(), true), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(MongoCollection.FIELD_PRIORITY, context.getPriority()).get()).get());
		return this;
	}

	@Override
	public Addressing activate(JIDContext context) {
		this.config.collection().update(this.buildQueryWithNecessaryFields(context), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(MongoCollection.FIELD_CURRENT, new Date().getTime()).get()).get());
		return this;
	}

	private JIDContexts find(JID jid, Boolean usingResource, Boolean usingOffline) {
		return new MongoUserContexts(jid, usingOffline, this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), MongoCollection.FILTER_INDEX).sort(MongoCollection.DEFAULT_SORTER));
	}

	private DBObject buildQueryWithNecessaryFields(JIDContext context) {
		DBObject query = BasicDBObjectBuilder.start().add(MongoCollection.FIELD_JID, context.getJid().asStringWithBare()).add(MongoCollection.FIELD_RESOURCE, context.getJid().getResource()).add(MongoCollection.FIELD_INDEX, context.getIndex()).get();
		this.log.debug("Query: " + query);
		return query;
	}

	private DBObject buildQueryWithSmartResource(JID jid, Boolean usingResource) {
		DBObject query = BasicDBObjectBuilder.start().add(MongoCollection.FIELD_JID, jid.asStringWithBare()).get();
		if (usingResource && jid.getResource() != null) {
			query.put("resource", jid.getResource());
		}
		this.log.debug("Query: " + query);
		return query;
	}

	private boolean exists(Long index) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start().add(MongoCollection.FIELD_INDEX, index).get(), MongoCollection.FILTER_INDEX) != null;
	}

	private class MongoUserContexts extends JIDContexts {

		private final static long serialVersionUID = 1L;

		private MongoUserContexts(JID jid, Boolean usingOffline, DBCursor cursor) {
			while (cursor.hasNext()) {
				JIDContext context = MongoAddressing.this.contexts.get(Long.class.cast(cursor.next().get(MongoCollection.FIELD_INDEX)));
				if (context != null) {
					this.add(context);
				}
			}
			this.usingOffline(jid, usingOffline);
		}

		private void usingOffline(JID jid, Boolean usingOffline) {
			if (usingOffline && super.isEmpty()) {
				super.add(MongoAddressing.this.offlineContextBuilder.build(jid, MongoAddressing.this.nothing));
			}
		}
	}

	private class JIDContextGC extends GC {

		protected JIDContextGC(Interval interval, ResourceMonitor resourceMonitor) {
			super(interval, resourceMonitor);
		}

		@Override
		public void gc() {
			for (Long index : MongoAddressing.this.contexts.keySet()) {
				if (!MongoAddressing.this.exists(index)) {
					JIDContext leak = MongoAddressing.this.contexts.get(index);
					MongoAddressing.this.leave(MongoAddressing.this.contexts.get(index));
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
