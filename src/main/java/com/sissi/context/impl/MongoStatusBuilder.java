package com.sissi.context.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.context.StatusBuilder;
import com.sissi.context.StatusClauses;

/**
 * 索引策略:{"index":-1,"jid":1,"resource":1}
 * 
 * @author kim 2013-11-21
 */
public class MongoStatusBuilder implements StatusBuilder {

	/**
	 * 过滤策略,{"avator":1,"show":1,"status":1,"type":1,"priority":1}
	 */
	private final DBObject filter = BasicDBObjectBuilder.start().add(StatusClauses.KEY_AVATOR, 1).add(StatusClauses.KEY_SHOW, 1).add(StatusClauses.KEY_STATUS, 1).add(StatusClauses.KEY_TYPE, 1).add(StatusClauses.KEY_PRIORITY, 1).get();

	private final MongoConfig config;

	public MongoStatusBuilder(MongoConfig config) {
		this(config, true);
	}

	public MongoStatusBuilder(MongoConfig config, boolean reset) {
		super();
		this.config = reset ? config.reset() : config;
	}

	@Override
	public Status build(JIDContext context) {
		return new MongoStatus(context);
	}

	/**
	 * 清除出席状态</p>{"index":Xxx,"jid":Xxx,"resource":Xxx}
	 * 
	 * @param context
	 * @return
	 */
	private MongoStatusBuilder reset(JIDContext context) {
		this.config.collection().remove(this.buildQuery(context));
		return this;
	}

	/**
	 * 全量更新</p>{"set":{"type":Xxx,"show":Xxx,"status":Xxx,"avator":Xxx,"priority":Xxx}}
	 * 
	 * @param context
	 * @param type
	 * @param show
	 * @param status
	 * @param avator
	 * @return
	 */
	private MongoStatusBuilder set(JIDContext context, String type, String show, String status, String avator) {
		this.config.collection().update(this.buildQuery(context), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start().add(StatusClauses.KEY_TYPE, type).add(StatusClauses.KEY_SHOW, show).add(StatusClauses.KEY_STATUS, status).add(StatusClauses.KEY_AVATOR, avator).add(Dictionary.FIELD_PRIORITY, context.priority()).get()).get());
		return this;
	}

	private StatusClauses get(JIDContext context) {
		return new MongoClauses(this.config.collection().findOne(this.buildQuery(context), this.filter));
	}

	/**
	 * {"index":Xxx,"jid":Xxx,"resource":Xxx}
	 * 
	 * @param context
	 * @return
	 */
	private DBObject buildQuery(JIDContext context) {
		return BasicDBObjectBuilder.start().add(Dictionary.FIELD_INDEX, context.index()).add(Dictionary.FIELD_JID, context.jid().asStringWithBare()).add(Dictionary.FIELD_RESOURCE, context.jid().resource()).get();
	}

	private class MongoStatus implements Status {

		private final JIDContext context;

		public MongoStatus(JIDContext context) {
			super();
			this.context = context;
		}

		public MongoStatus clear() {
			MongoStatusBuilder.this.reset(this.context);
			return this;
		}

		public Status clauses(StatusClauses clauses) {
			MongoStatusBuilder.this.set(this.context, clauses.find(StatusClauses.KEY_TYPE), clauses.find(StatusClauses.KEY_SHOW), clauses.find(StatusClauses.KEY_STATUS), clauses.find(StatusClauses.KEY_AVATOR));
			return this;
		}

		@Override
		public StatusClauses clauses() {
			return MongoStatusBuilder.this.get(this.context);
		}
	}

	private class MongoClauses implements StatusClauses {

		private final DBObject status;

		public MongoClauses(DBObject status) {
			super();
			this.status = status;
		}

		@Override
		public String find(String key) {
			return MongoUtils.asString(this.status, key);
		}
	}
}
