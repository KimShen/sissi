package com.sissi.resource.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.resource.ResourceCounter;

/**
 * @author kim 2014年1月15日
 */
public class MongoResourceCounter implements ResourceCounter {

	private final static DBObject INCR = BasicDBObjectBuilder.start("$inc", BasicDBObjectBuilder.start("incr", 1).get()).get();

	private final static DBObject DECR = BasicDBObjectBuilder.start("$inc", BasicDBObjectBuilder.start("decr", 1).get()).get();

	private final static Log LOG = LogFactory.getLog(MongoResourceCounter.class);

	private final static String RESOURCE = "resource";

	private final MongoConfig config;

	private final String resource;

	public MongoResourceCounter(MongoConfig config, String resource) {
		super();
		this.config = config;
		this.resource = resource;
	}

	@Override
	public ResourceCounter increment() {
		return this.statistics(INCR);
	}

	@Override
	public ResourceCounter decrement() {
		return this.statistics(DECR);
	}

	private ResourceCounter statistics(DBObject op) {
		this.config.collection().update(this.buildQuery(this.resource), op, true, false, WriteConcern.SAFE);
		return this;
	}

	public DBObject buildQuery(String resource) {
		DBObject query = BasicDBObjectBuilder.start(RESOURCE, resource).get();
		LOG.debug("Query is: " + query);
		return query;
	}
}
