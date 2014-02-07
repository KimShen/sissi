package com.sissi.addressing.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.addressing.Addressing;
import com.sissi.commons.Interval;
import com.sissi.commons.Runner;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoProxyConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.JIDs;
import com.sissi.context.impl.JIDContexts;
import com.sissi.gc.GC;
import com.sissi.resource.ResourceCounter;

/**
 * @author kim 2014年1月29日
 */
public class MongoAddressing implements Addressing {

	private final Integer gcThreadNumber = 1;

	private final String fieldIndex = "index";

	private final String fieldAddress = "address";

	private final DBObject filterIndex = BasicDBObjectBuilder.start(this.fieldIndex, 1).get();

	private final DBObject filterResource = BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_RESOURCE, 1).get();

	private final DBObject sortPriority = BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_PRIORITY, -1).get();

	private final Log log = LogFactory.getLog(this.getClass());

	private final JIDContextParam nothing = new NothingJIDContextParam();

	private final Map<Long, JIDContext> contexts = new ConcurrentHashMap<Long, JIDContext>();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	private final JIDContextBuilder offline;

	public MongoAddressing(Runner runner, Interval interval, MongoConfig config, JIDBuilder jidBuilder, ResourceCounter resourceCounter, JIDContextBuilder offlineContextBuilder) {
		super();
		this.config = config.clear();
		this.jidBuilder = jidBuilder;
		this.offline = offlineContextBuilder;
		runner.executor(this.gcThreadNumber, new JIDContextGC(interval, resourceCounter));
	}

	@Override
	public Addressing join(JIDContext context) {
		this.contexts.put(context.index(), context);
		this.config.collection().save(this.buildQueryWithNecessaryFields(context));
		return this;
	}

	public Addressing leave(JID jid) {
		return this.leave(jid, true);
	}

	public Addressing leave(JID jid, Boolean usingResource) {
		for (JIDContext current : this.find(jid, usingResource, false)) {
			this.leave(current);
		}
		return this;
	}

	@Override
	public Addressing leave(JIDContext context) {
		if (context.close()) {
			this.config.collection().remove(this.buildQueryWithNecessaryFields(this.contexts.remove(context.index())));
		}
		return this;
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
		DBObject entity = this.config.collection().findOne(this.buildQueryWithSmartResource(jid, usingResource), this.filterIndex);
		// Assert: Not find with findOne(jid,resource) should not find with find(jid,resource)
		return entity != null ? this.contexts.get(Long.class.cast(entity.get(this.fieldIndex))) : this.find(jid);
	}

	public JIDs resources(JID jid) {
		return this.resources(jid, false);
	}

	public JIDs resources(JID jid, Boolean usingResource) {
		return new Resources(jid, this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), this.filterResource));
	}

	public Addressing priority(JIDContext context) {
		this.config.collection().update(this.buildQueryWithSmartResource(context.jid(), true), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_PRIORITY, context.priority()).get()).get());
		return this;
	}

	private JIDContexts find(JID jid, Boolean usingResource, Boolean usingOffline) {
		return new MongoJIDContexts(jid, usingOffline, this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), this.filterIndex).sort(this.sortPriority));
	}

	private DBObject buildQueryWithNecessaryFields(JIDContext context) {
		return BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_JID, context.jid().asStringWithBare()).add(MongoProxyConfig.FIELD_RESOURCE, context.jid().resource()).add(this.fieldIndex, context.index()).add(this.fieldAddress, context.address().toString()).get();
	}

	private DBObject buildQueryWithSmartResource(JID jid, Boolean usingResource) {
		BasicDBObjectBuilder query = BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_JID, jid.asStringWithBare());
		if (usingResource && jid.resource() != null) {
			query.add(MongoProxyConfig.FIELD_RESOURCE, jid.resource());
		}
		return query.get();
	}

	private boolean exists(Long index) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start(this.fieldIndex, index).get(), MongoAddressing.this.filterIndex) != null;
	}

	private class Resources implements JIDs {

		private final AtomicInteger counter = new AtomicInteger();

		private final JID jid;

		private final String[] resources;

		private Resources(JID source, DBCursor cursor) {
			this.jid = MongoAddressing.this.jidBuilder.build(source.asStringWithBare());
			this.resources = new String[cursor.count()];
			for (int index = 0; cursor.hasNext(); index++) {
				this.resources[index] = MongoAddressing.this.config.asString(cursor.next(), MongoProxyConfig.FIELD_RESOURCE);
			}
		}

		public Iterator<JID> iterator() {
			return new JIDIterator();
		}

		public boolean isEmpty() {
			return this.resources == null || this.resources.length == 0;
		}

		public boolean moreThan(Integer counter) {
			return this.resources.length >= counter;
		}

		public boolean lessThan(Integer counter) {
			return this.resources.length <= counter;
		}

		private boolean hasNext() {
			return this.counter.get() > (this.resources.length - 1);
		}

		private JID next() {
			return this.jid.resource(this.resources[this.counter.incrementAndGet() - 1]);
		}

		private class JIDIterator implements Iterator<JID> {

			@Override
			public boolean hasNext() {
				return Resources.this.hasNext();
			}

			@Override
			public JID next() {
				return Resources.this.next();
			}

			@Override
			public void remove() {
			}
		}
	}

	private class MongoJIDContexts extends JIDContexts {

		private final static long serialVersionUID = 1L;

		private MongoJIDContexts(JID jid, Boolean usingOffline, DBCursor cursor) {
			while (cursor.hasNext()) {
				JIDContext context = MongoAddressing.this.contexts.get(Long.class.cast(cursor.next().get(MongoAddressing.this.fieldIndex)));
				if (context != null) {
					super.add(context);
				}
			}
			this.usingOffline(jid, usingOffline);
		}

		private void usingOffline(JID jid, Boolean usingOffline) {
			if (usingOffline && super.isEmpty()) {
				super.add(MongoAddressing.this.offline.build(jid, MongoAddressing.this.nothing));
			}
		}
	}

	private class JIDContextGC extends GC {

		private JIDContextGC(Interval interval, ResourceCounter resourceCounter) {
			super(interval, JIDContextGC.class, resourceCounter);
		}

		@Override
		public boolean gc() {
			for (Long index : MongoAddressing.this.contexts.keySet()) {
				if (!MongoAddressing.this.exists(index) && MongoAddressing.this.contexts.get(index).close()) {
					MongoAddressing.this.log.warn("Find leak context: " + index);
				}
			}
			return true;
		}
	}

	private class NothingJIDContextParam implements JIDContextParam {

		@Override
		public <T> T find(String key, Class<T> clazz) {
			return null;
		}
	}
}
