package com.sissi.resource.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.resource.ResourceCounter;

/**
 * 索引策略: {"resource":Xxx}
 * 
 * @author kim 2014年1月15日
 */
public class MongoResourceCounter implements ResourceCounter {

	/**
	 * {"$inc":{"count":1}}
	 */
	private final DBObject incr = BasicDBObjectBuilder.start("$inc", BasicDBObjectBuilder.start("count", 1).get()).get();

	/**
	 * {"$inc":{"count":1}}
	 */
	private final DBObject decr = BasicDBObjectBuilder.start("$inc", BasicDBObjectBuilder.start("count", -1).get()).get();

	private final String resource = "resource";

	private final MongoConfig config;

	public MongoResourceCounter(MongoConfig config) {
		super();
		this.config = config.reset();
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
		this.config.collection().update(BasicDBObjectBuilder.start(this.resource, resource).get(), op, true, false);
		return this;
	}
}
