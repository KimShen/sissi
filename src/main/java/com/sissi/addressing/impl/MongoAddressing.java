package com.sissi.addressing.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.addressing.Addressing;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.JIDs;
import com.sissi.context.impl.JIDContexts;
import com.sissi.context.impl.ShareJIDs;

/**
 * 基于ConcurrentHashMap的寻址策略</p>索引策略1:{"jid":1},索引策略2:{"jid":1,"resource":1,"priority":-1},索引策略3:{"index":-1,"jid":1,"resource":1}</p>
 * 
 * @author kim 2014年1月29日
 */
public class MongoAddressing implements Addressing {

	private final String fieldAddress = "address";

	/**
	 * 过滤策略,{"index":1}
	 */
	private final DBObject filterIndex = BasicDBObjectBuilder.start(Dictionary.FIELD_INDEX, 1).get();

	/**
	 * 过滤策略,{"resource":1}
	 */
	private final DBObject filterResource = BasicDBObjectBuilder.start(Dictionary.FIELD_RESOURCE, 1).get();

	/**
	 * 排序策略,{"priority":-1}
	 */
	private final DBObject querySort = BasicDBObjectBuilder.start(Dictionary.FIELD_PRIORITY, -1).get();

	private final JIDContextParam nothing = new NothingJIDContextParam();

	private final Map<Long, JIDContext> contexts = new ConcurrentHashMap<Long, JIDContext>();

	private final MongoConfig config;

	private final JIDContextBuilder offline;

	public MongoAddressing(MongoConfig config, JIDContextBuilder offline) {
		super();
		this.offline = offline;
		this.config = config.reset();
	}

	@Override
	public Addressing join(JIDContext context) {
		if (MongoUtils.success(this.config.collection().save(this.buildQueryWithNecessaryFields(context), WriteConcern.SAFE))) {
			this.contexts.put(context.index(), context);
		}
		return this;
	}

	@Override
	public Addressing leave(JIDContext context) {
		if (MongoUtils.success(this.config.collection().remove(this.buildQueryWithNecessaryFields(context), WriteConcern.SAFE))) {
			this.contexts.remove(context.index());
		}
		return this;
	}

	/*
	 * {"$set":{"priority":Xxx}}
	 * 
	 * @see com.sissi.addressing.Addressing#priority(com.sissi.context.JIDContext)
	 */
	public Addressing priority(JIDContext context) {
		this.config.collection().update(this.buildQueryWithSmartResource(context.jid(), true), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(Dictionary.FIELD_PRIORITY, context.priority()).get()).get());
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

	/*
	 * 1,符合条件唯一JIDContext</p>2,不存在则</p>2.1,使用离线JIDContext</p>2.2,使用Find(All)
	 * 
	 * @see com.sissi.addressing.Addressing#findOne(com.sissi.context.JID, boolean, boolean)
	 */
	public JIDContext findOne(JID jid, boolean usingResource, boolean offline) {
		DBObject entity = this.config.collection().findOne(this.buildQueryWithSmartResource(jid, usingResource), this.filterIndex);
		return entity != null ? this.contexts.get(MongoUtils.asLong(entity, Dictionary.FIELD_INDEX)) : offline ? this.offline.build(jid, this.nothing) : this.find(jid);
	}

	public JIDs resources(JID jid) {
		return this.resources(jid, false);
	}

	public JIDs resources(JID jid, boolean usingResource) {
		return new ShareJIDs(jid, this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), this.filterResource));
	}

	private JIDContexts find(JID jid, boolean usingResource, boolean usingOffline) {
		return new MongoJIDContexts(jid, usingOffline, this.config.collection().find(this.buildQueryWithSmartResource(jid, usingResource), this.filterIndex).sort(this.querySort));
	}

	/**
	 * For Join / Remove</p>{"index":Xxx,"jid":Xxx,"resource":Xxx,"address":Xxx}
	 * 
	 * @param context
	 * @return
	 */
	private DBObject buildQueryWithNecessaryFields(JIDContext context) {
		return BasicDBObjectBuilder.start().add(Dictionary.FIELD_INDEX, context.index()).add(Dictionary.FIELD_JID, context.jid().asStringWithBare()).add(Dictionary.FIELD_RESOURCE, context.jid().resource()).add(this.fieldAddress, context.address().toString()).get();
	}

	/**
	 * For FindXxx</p>{"jid",Xxx,"resource":Xxx(如果使用资源)}
	 * 
	 * @param jid
	 * @param usingResource
	 * @return
	 */
	private DBObject buildQueryWithSmartResource(JID jid, boolean usingResource) {
		// JID,Resource
		BasicDBObjectBuilder query = BasicDBObjectBuilder.start(Dictionary.FIELD_JID, jid.asStringWithBare());
		if (usingResource && !jid.isBare()) {
			query.add(Dictionary.FIELD_RESOURCE, jid.resource());
		}
		return query.get();
	}

	private class MongoJIDContexts extends JIDContexts {

		private final static long serialVersionUID = 1L;

		private MongoJIDContexts(JID jid, boolean usingOffline, DBCursor cursor) {
			this.read(cursor).usingOffline(jid, usingOffline);
		}

		private MongoJIDContexts read(DBCursor cursor) {
			try (DBCursor iterator = cursor) {
				while (iterator.hasNext()) {
					JIDContext context = MongoAddressing.this.contexts.get(MongoUtils.asLong(iterator.next(), Dictionary.FIELD_INDEX));
					// Double check 4 multi thread
					if (context != null) {
						super.add(context);
					}
				}
			}
			return this;
		}

		private MongoJIDContexts usingOffline(JID jid, boolean usingOffline) {
			if (usingOffline && super.isEmpty()) {
				super.add(MongoAddressing.this.offline.build(jid, MongoAddressing.this.nothing));
			}
			return this;
		}
	}

	private class NothingJIDContextParam implements JIDContextParam {

		@Override
		public <T> T find(String key, Class<T> clazz) {
			return null;
		}
	}
}
