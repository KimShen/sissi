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

	private final static Map<String, Object> OPTIONS = new HashMap<String, Object>();

	private final Map<String, String> configs = new HashMap<String, String>();

	private final DBCollection collection;

	private final DBObject indexes;

	private final DBObject options;

	private final MongoClient client;

	public MongoCollection(MongoClient client, String db, String collection, Map<String, Object> indexes) {
		this(client, db, collection, true, indexes, OPTIONS);
	}

	public MongoCollection(MongoClient client, String db, String collection, Boolean indexIfNotExists, Map<String, Object> indexes) {
		this(client, db, collection, indexIfNotExists, indexes, OPTIONS);
	}

	public MongoCollection(MongoClient client, String db, String collection, Map<String, Object> indexes, Map<String, Object> options) {
		this(client, db, collection, true, indexes, options);
	}

	public MongoCollection(MongoClient client, String db, String collection, Boolean indexIfNotExists, Map<String, Object> indexes, Map<String, Object> options) {
		super();
		this.client = client;
		this.configs.put(MongoCollection.D_NAME, db);
		this.configs.put(MongoCollection.C_NAME, collection);
		this.collection = this.client.getDB(db).getCollection(collection);
		this.indexes = BasicDBObjectBuilder.start(indexes).get();
		this.options = BasicDBObjectBuilder.start(options).get();
		if (indexIfNotExists) {
			this.indexIfNotExists(db, collection);
		}
	}

	private void indexIfNotExists(String db, String collection) {
		if (!this.client.getDB(db).getCollectionNames().contains(collection)) {
			this.index();
		}
	}

	@Override
	public String get(String key) {
		return this.configs.get(key);
	}

	public MongoConfig rebuild() {
		this.find().drop();
		this.index();
		return this;
	}

	public MongoConfig index() {
		if (!this.indexes.keySet().isEmpty()) {
			this.find().ensureIndex(this.indexes, this.options);
		}
		return this;
	}

	public DBCollection find() {
		return this.collection;
	}

	public String asString(DBObject db, String key) {
		if (db == null) {
			return null;
		}
		Object value = db.get(key);
		return value != null ? value.toString() : null;
	}

	public Boolean asBoolean(DBObject db, String key) {
		if (db == null) {
			return null;
		}
		Object value = db.get(key);
		return value != null ? (Boolean) value : Boolean.FALSE;

	}
}
