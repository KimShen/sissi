package com.sissi.config.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.sissi.config.MongoCollection;
import com.sissi.config.MongoConfig;

/**
 * @author kim 2013-11-15
 */
public class MongoProxyConfig implements MongoConfig {

	public static final String FIELD_JID = "jid";

	public static final String FIELD_NICK = "nick";

	public static final String FIELD_STATE = "state";

	public static final String FIELD_INDEX = "index";

	public static final String FIELD_CREATOR = "creator";

	public static final String FIELD_PRIORITY = "priority";

	public static final String FIELD_RESOURCE = "resource";

	public static final String FIELD_USERNAME = "username";

	private static final DBObject clear = BasicDBObjectBuilder.start().get();

	private static final Log log = LogFactory.getLog(MongoProxyConfig.class);

	private final Map<String, String> configs;
	
	private final MongoWrapCollection wrap;

	private final DBCollection collection;

	public MongoProxyConfig(MongoClient client, String db, String collection) {
		super();
		Map<String, String> configs = new HashMap<String, String>();
		configs.put(MongoProxyConfig.D_NAME, db);
		configs.put(MongoProxyConfig.C_NAME, collection);
		this.configs = Collections.unmodifiableMap(configs);
		this.wrap = new MongoWrapCollection();
		this.collection = client.getDB(db).getCollection(collection);
	}

	@Override
	public String get(String key) {
		return this.configs.get(key);
	}

	public MongoConfig clear() {
		this.collection().remove(clear);
		return this;
	}

	public MongoWrapCollection collection() {
		return this.wrap;
	}

	private class MongoWrapCollection implements MongoCollection {

		private void logUpdate(DBObject query, DBObject entity) {
			log.debug("Update: " + query + " / " + entity);
		}

		public WriteResult remove(DBObject query) {
			log.debug("Remove: " + query);
			return MongoProxyConfig.this.collection.remove(query);
		}

		@Override
		public WriteResult save(DBObject entity) {
			log.debug("Save: " + entity);
			return MongoProxyConfig.this.collection.save(entity);
		}

		public WriteResult save(DBObject entity, WriteConcern concern) {
			log.debug("Save: " + entity);
			return MongoProxyConfig.this.collection.save(entity, concern);
		}

		@Override
		public WriteResult update(DBObject query, DBObject entity) {
			this.logUpdate(query, entity);
			return MongoProxyConfig.this.collection.update(query, entity);
		}

		@Override
		public WriteResult update(DBObject query, DBObject entity, boolean upsert, boolean batch) {
			this.logUpdate(query, entity);
			return MongoProxyConfig.this.collection.update(query, entity, upsert, batch);
		}

		public WriteResult update(DBObject query, DBObject entity, boolean upsert, boolean batch, WriteConcern concern) {
			this.logUpdate(query, entity);
			return MongoProxyConfig.this.collection.update(query, entity, upsert, batch, concern);
		}

		@Override
		public DBCursor find(DBObject query) {
			log.debug("Find: " + query);
			return MongoProxyConfig.this.collection.find(query);
		}

		@Override
		public DBCursor find(DBObject query, DBObject filter) {
			log.debug("Find: " + query + " / Filter: " + filter);
			return MongoProxyConfig.this.collection.find(query, filter);
		}

		@Override
		public DBObject findOne(DBObject query) {
			log.debug("FindOne: " + query);
			return MongoProxyConfig.this.collection.findOne(query);
		}

		@Override
		public DBObject findOne(DBObject query, DBObject filter) {
			log.debug("FindOne: " + query + " / Filter: " + filter);
			return MongoProxyConfig.this.collection.findOne(query, filter);
		}

		public AggregationOutput aggregate(DBObject firstOp, DBObject... additionalOps) {
			log.debug("Aggregate: " + firstOp + " / " + Arrays.toString(additionalOps));
			return MongoProxyConfig.this.collection.aggregate(firstOp, additionalOps);
		}
	}
}
