package com.sissi.monitor.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.monitor.Monitor;

/**
 * @author kim 2014年1月15日
 */
public class MongoServerMonitor implements Monitor {

	private final String INCR = "open";

	private final String DECR = "close";

	private final Log log = LogFactory.getLog(this.getClass());

	private final MongoConfig config;

	public MongoServerMonitor(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public Boolean increment(String key, Long num) {
		return this.config.collection().update(this.buildKey(INCR, key), this.buildEntity(key, num), true, false, WriteConcern.SAFE).getN() != 0;
	}

	@Override
	public Boolean decrement(String key, Long num) {
		return this.increment(key, 0 - num);
	}

	private DBObject buildEntity(String key, Long num) {
		DBObject entity = BasicDBObjectBuilder.start(key, BasicDBObjectBuilder.start("$inc", num).get()).get();
		this.log.debug("Entity is: " + entity);
		return entity;
	}

	private DBObject buildKey(String key, String type) {
		DBObject query = BasicDBObjectBuilder.start("key", key + type).get();
		this.log.debug("Query is: " + query);
		return query;
	}
}
