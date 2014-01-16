package com.sissi.resource.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.resource.ResourceMonitor;

/**
 * @author kim 2014年1月15日
 */
public class MongoResourceMonitor implements ResourceMonitor {

	private final static DBObject INCR = BasicDBObjectBuilder.start("$inc", BasicDBObjectBuilder.start("incr", 1).get()).get();

	private final static DBObject DECR = BasicDBObjectBuilder.start("$inc", BasicDBObjectBuilder.start("decr", 1).get()).get();

	private final static Log LOG = LogFactory.getLog(MongoResourceMonitor.class);

	private final static String RESOURCE = "resource";

	private final MongoConfig config;

	private final String resource;

	public MongoResourceMonitor(MongoConfig config, String resource) {
		super();
		this.config = config.clear();
		this.resource = resource;
	}

	@Override
	public ResourceMonitor increment() {
		return this.statistics(INCR);
	}

	@Override
	public ResourceMonitor decrement() {
		return this.statistics(DECR);
	}

	private ResourceMonitor statistics(DBObject op) {
		this.config.collection().update(this.buildQuery(resource), op, true, false);
		return this;
	}

	public DBObject buildQuery(String resource) {
		DBObject query = BasicDBObjectBuilder.start(RESOURCE, resource).get();
		LOG.debug("Query is: " + query);
		return query;
	}
}
