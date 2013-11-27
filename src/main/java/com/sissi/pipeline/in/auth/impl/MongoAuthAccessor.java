package com.sissi.pipeline.in.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.config.Config;
import com.sissi.pipeline.in.auth.AuthAccessor;
import com.sissi.util.MongoUtils;

/**
 * @author kim 2013-11-6
 */
public class MongoAuthAccessor implements AuthAccessor {

	private final Log log = LogFactory.getLog(this.getClass());

	private Config config;

	private MongoClient client;

	public MongoAuthAccessor(Config config, MongoClient client) {
		super();
		this.config = config;
		this.client = client;
	}

	@Override
	public String access(String username) {
		DBObject query = BasicDBObjectBuilder.start().add("username", username).get();
		this.log.debug("Query: " + query);
		DBObject entity = MongoUtils.findCollection(this.config, this.client).findOne(query);
		this.log.debug("User for: " + username + " is " + entity);
		return entity != null ? entity.get("password").toString() : null;
	}
}
