package com.sissi.config.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.sissi.config.MongoCollection;
import com.sissi.config.MongoConfig;

/**
 * 
 * @author kim 2013-11-15
 */
public class MongoProxyConfig implements MongoConfig {

	/**
	 * 重置数据集合(All)
	 */
	private final static DBObject queryClear = BasicDBObjectBuilder.start().get();

	private final static Log log = LogFactory.getLog(MongoProxyConfig.class);

	private final Map<String, Object> configs = new HashMap<String, Object>();

	private final MongoWrapCollection wrap;

	private final DBCollection collection;

	public MongoProxyConfig(Mongo client, String db, String collection) {
		super();
		this.wrap = new MongoWrapCollection();
		this.collection = client.getDB(db).getCollection(collection);
		this.configs.put(MongoConfig.D_NAME, this.collection.getDB());
		this.configs.put(MongoConfig.C_NAME, this.collection);
	}

	@Override
	public Object get(String key) {
		return this.configs.get(key);
	}

	/*
	 * remove({})
	 * 
	 * @see com.sissi.config.MongoConfig#reset()
	 */
	public MongoProxyConfig reset() {
		this.collection().remove(queryClear);
		return this;
	}

	public MongoWrapCollection collection() {
		return this.wrap;
	}

	private class MongoWrapCollection implements MongoCollection {

		private void log(String op, DBObject first, DBObject... entity) {
			log.debug(op + ": " + first + entity != null ? (" / " + Arrays.toString(entity)) : "");
		}

		public WriteResult remove(DBObject query) {
			return this.remove(query, WriteConcern.NONE);
		}

		public WriteResult remove(DBObject query, WriteConcern concern) {
			this.log("remove", query);
			return MongoProxyConfig.this.collection.remove(query, concern);
		}

		@Override
		public WriteResult save(DBObject entity) {
			return this.save(entity, WriteConcern.NONE);
		}

		public WriteResult save(DBObject entity, WriteConcern concern) {
			this.log("save", entity);
			return MongoProxyConfig.this.collection.save(entity, concern);
		}

		@Override
		public WriteResult update(DBObject query, DBObject entity) {
			return this.update(query, entity, false, false, WriteConcern.NONE);
		}

		@Override
		public WriteResult update(DBObject query, DBObject entity, boolean upsert, boolean batch) {
			return this.update(query, entity, upsert, batch, WriteConcern.NONE);
		}

		public WriteResult update(DBObject query, DBObject entity, boolean upsert, boolean batch, WriteConcern concern) {
			this.log("update", query, entity);
			return MongoProxyConfig.this.collection.update(query, entity, upsert, batch, concern);
		}

		@Override
		public DBCursor find(DBObject query) {
			this.log("find", query);
			return MongoProxyConfig.this.collection.find(query);
		}

		@Override
		public DBCursor find(DBObject query, DBObject filter) {
			this.log("find", query);
			return MongoProxyConfig.this.collection.find(query, filter);
		}

		@Override
		public DBObject findOne(DBObject query) {
			this.log("findOne", query);
			return MongoProxyConfig.this.collection.findOne(query);
		}

		@Override
		public DBObject findOne(DBObject query, DBObject filter) {
			this.log("findOne", query);
			return MongoProxyConfig.this.collection.findOne(query, filter);
		}

		public DBObject findAndModify(DBObject query, DBObject entity) {
			this.log("findOne", query);
			return MongoProxyConfig.this.collection.findAndModify(query, entity);
		}

		public AggregationOutput aggregate(DBObject firstOp, DBObject... additionalOps) {
			this.log("aggregate", firstOp, additionalOps);
			return MongoProxyConfig.this.collection.aggregate(firstOp, additionalOps);
		}
	}
}
