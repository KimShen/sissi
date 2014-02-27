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
import com.sissi.commons.Extracter;
import com.sissi.commons.Trace;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.JIDs;
import com.sissi.context.impl.JIDContexts;
import com.sissi.gc.GC;
import com.sissi.resource.ResourceCounter;
import com.sissi.thread.Interval;
import com.sissi.thread.Runner;

/**
 * @author kim 2014年1月29日
 */
public class MongoAddressing implements Addressing {

	private final String fieldAddress = "address";

	private final DBObject sort = BasicDBObjectBuilder.start(MongoConfig.FIELD_PRIORITY, -1).get();

	private final DBObject filterIndex = BasicDBObjectBuilder.start(MongoConfig.FIELD_INDEX, 1).get();

	private final DBObject filterResource = BasicDBObjectBuilder.start(MongoConfig.FIELD_RESOURCE, 1).get();

	private final Log log = LogFactory.getLog(this.getClass());

	private final JIDContextParam nothing = new NothingJIDContextParam();

	private final Map<Long, JIDContext> contexts = new ConcurrentHashMap<Long, JIDContext>();

	private final MongoConfig config;

	private final JIDContextBuilder offline;

	public MongoAddressing(Runner runner, Interval interval, MongoConfig config, JIDContextBuilder jidContextBuilder, ResourceCounter resourceCounter) {
		super();
		this.config = config.clear();
		this.offline = jidContextBuilder;
		runner.executor(1, new JIDContextGC(interval, resourceCounter));
	}

	@Override
	public Addressing join(JIDContext context) {
		// First memory then db
		this.contexts.put(context.index(), context);
		this.config.collection().save(this.buildQueryWithNecessaryFields(context));
		return this;
	}

	@Override
	public Addressing leave(JIDContext context) {
		if (this.contexts.remove(context.index()) != null) {
			this.config.collection().remove(this.buildQueryWithNecessaryFields(context));
		}
		return this;
	}

	public Addressing priority(JIDContext context) {
		this.config.collection().update(this.buildQueryWithSmartResource(context.jid(), true), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(MongoConfig.FIELD_PRIORITY, context.priority()).get()).get());
		return this;
	}

	@Override
	public JIDContexts find(JID jid) {
		return this.find(jid, false);
	}

	@Override
	public JIDContexts find(JID jid, boolean usingResource) {
		return this.find(jid, usingResource, true);
	}

	@Override
	public JIDContext findOne(JID jid) {
		return this.findOne(jid, false);
	}

	public JIDContext findOne(JID jid, boolean usingResource) {
		return this.findOne(jid, usingResource, false);
	}

	public JIDContext findOne(JID jid, boolean usingResource, boolean offline) {
		DBObject entity = this.config.collection().findOne(this.buildQueryWithSmartResource(jid, usingResource), this.filterIndex);
		return entity != null ? this.contexts.get(Extracter.asLong(entity, MongoConfig.FIELD_INDEX)) : offline ? this.offline.build(jid, this.nothing) : this.find(jid);
	}

	public JIDs resources(JID jid) {
		return this.resources(jid, false);
	}

	public JIDs resources(JID jid, boolean usingResource) {
		return new Resources(jid, this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), this.filterResource));
	}

	private JIDContexts find(JID jid, boolean usingResource, boolean usingOffline) {
		return new MongoJIDContexts(jid, usingOffline, this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), this.filterIndex).sort(this.sort));
	}

	private DBObject buildQueryWithNecessaryFields(JIDContext context) {
		return BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, context.jid().asStringWithBare()).add(MongoConfig.FIELD_RESOURCE, context.jid().resource()).add(MongoConfig.FIELD_INDEX, context.index()).add(this.fieldAddress, context.address().toString()).get();
	}

	private DBObject buildQueryWithSmartResource(JID jid, boolean usingResource) {
		BasicDBObjectBuilder query = BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, jid.asStringWithBare());
		if (usingResource && !jid.isBare()) {
			query.add(MongoConfig.FIELD_RESOURCE, jid.resource());
		}
		return query.get();
	}

	private boolean exists(Long index) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start(MongoConfig.FIELD_INDEX, index).get(), MongoAddressing.this.filterIndex) != null;
	}

	private class Resources implements JIDs {

		private final AtomicInteger counter = new AtomicInteger();

		private final String[] resources;

		private final JID jid;

		private Resources(JID source, DBCursor cursor) {
			try {
				this.jid = source.bare();
				this.resources = new String[cursor.count()];
				for (int index = 0; cursor.hasNext(); index++) {
					this.resources[index] = Extracter.asString(cursor.next(), MongoConfig.FIELD_RESOURCE);
				}
			} finally {
				cursor.close();
			}
		}

		public Iterator<JID> iterator() {
			return new JIDIterator();
		}

		public boolean isEmpty() {
			return this.resources.length == 0;
		}

		public boolean lessThan(Integer counter) {
			return this.resources.length < counter;
		}

		private boolean hasNext() {
			return this.counter.get() < this.resources.length;
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

		private MongoJIDContexts(JID jid, boolean usingOffline, DBCursor cursor) {
			try {
				while (cursor.hasNext()) {
					JIDContext context = MongoAddressing.this.contexts.get(Extracter.asLong(cursor.next(), MongoConfig.FIELD_INDEX));
					// Double check for multi thread
					if (context != null) {
						super.add(context);
					}
				}
				this.usingOffline(jid, usingOffline);
			} finally {
				cursor.close();
			}
		}

		private void usingOffline(JID jid, boolean usingOffline) {
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
			for (long index : MongoAddressing.this.contexts.keySet()) {
				if (!MongoAddressing.this.exists(index)) {
					JIDContext leak = MongoAddressing.this.contexts.get(index);
					// Double check for multi thread
					if (leak != null) {
						MongoAddressing.this.log.warn("Find leak context: " + index);
						try {
							leak.close();
						} catch (Exception e) {
							MongoAddressing.this.log.warn(e.toString());
							Trace.trace(MongoAddressing.this.log, e);
						}
					}
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
