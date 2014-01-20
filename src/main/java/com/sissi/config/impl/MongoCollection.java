package com.sissi.config.impl;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.config.MongoConfig;

/**
 * @author kim 2013-11-15
 */
public class MongoCollection implements MongoConfig {

	public static final String FIELD_JID = "jid";

	public static final String FIELD_INDEX = "index";

	public static final String FIELD_PRIORITY = "priority";

	public static final String FIELD_RESOURCE = "resource";

	public static final String FIELD_BLOCK = "blocks";

	public static final String FIELD_STATE = "state";

	public static final String FIELD_SLAVE = "slave";

	public static final String FIELD_MASTER = "master";

	public static final DBObject FILTER_ID = BasicDBObjectBuilder.start("_id", 1).get();

	public static final DBObject FILTER_INDEX = BasicDBObjectBuilder.start(FIELD_INDEX, 1).get();

	public static final DBObject FILTER_BLOCKS = BasicDBObjectBuilder.start(FIELD_BLOCK, 1).get();

	public static final DBObject FILTER_MASTER = BasicDBObjectBuilder.start(FIELD_MASTER, 1).get();

	public static final DBObject FILTER_SLAVE = BasicDBObjectBuilder.start(FIELD_SLAVE, 1).get();

	public static final DBObject SORT_DEFAULT = BasicDBObjectBuilder.start().add(FIELD_PRIORITY, -1).get();

	private static final DBObject CLEAR = BasicDBObjectBuilder.start().get();

	private final Map<String, String> configs = new HashMap<String, String>();

	private final DBCollection collection;

	public MongoCollection(MongoClient client, String db, String collection) {
		super();
		this.configs.put(MongoCollection.D_NAME, db);
		this.configs.put(MongoCollection.C_NAME, collection);
		this.collection = client.getDB(db).getCollection(collection);
	}

	@Override
	public String get(String key) {
		return this.configs.get(key);
	}

	public MongoConfig clear() {
		this.collection().remove(CLEAR);
		return this;
	}

	public DBCollection collection() {
		return this.collection;
	}

	public String asString(DBObject db, String key) {
		Object value = this.as(db, key);
		return value != null ? value.toString() : null;
	}

	public Boolean asBoolean(DBObject db, String key) {
		Object value = this.as(db, key);
		return value != null ? Boolean.class.cast(value) : Boolean.FALSE;
	}

	private Object as(DBObject db, String key) {
		return db != null ? db.get(key) : null;
	}
}
