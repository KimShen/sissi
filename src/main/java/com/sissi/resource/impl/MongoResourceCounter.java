package com.sissi.resource.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.resource.ResourceCounter;

/**
 * @author kim 2014年1月15日
 */
public class MongoResourceCounter implements ResourceCounter {

	private final DBObject incr = BasicDBObjectBuilder.start("$inc", BasicDBObjectBuilder.start("incr", 1).get()).get();

	private final DBObject decr = BasicDBObjectBuilder.start("$inc", BasicDBObjectBuilder.start("decr", 1).get()).get();

	private final String resource = "resource";

	private final MongoConfig config;

	public MongoResourceCounter(MongoConfig config) {
		super();
		this.config = config.clear();
	}

	@Override
	public ResourceCounter increment(String resource) {
		return this.statistics(this.incr, resource);
	}

	@Override
	public ResourceCounter decrement(String resource) {
		return this.statistics(this.decr, resource);
	}

	private ResourceCounter statistics(DBObject op, String resource) {
		this.config.collection().update(this.buildQuery(resource), op, true, false);
		return this;
	}

	public DBObject buildQuery(String resource) {
		return BasicDBObjectBuilder.start(this.resource, resource).get();
	}
}
