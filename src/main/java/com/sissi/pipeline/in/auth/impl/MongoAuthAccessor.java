package com.sissi.pipeline.in.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.pipeline.in.auth.AuthAccessor;
<<<<<<< HEAD
=======
import com.sissi.util.MongoUtils;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

/**
 * @author kim 2013-11-6
 */
public class MongoAuthAccessor implements AuthAccessor {

	private final Log log = LogFactory.getLog(this.getClass());

	private MongoConfig config;

	public MongoAuthAccessor(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public String access(String username) {
		DBObject query = BasicDBObjectBuilder.start().add("username", username).get();
		this.log.debug("Query: " + query);
<<<<<<< HEAD
		DBObject entity = this.config.findCollection().findOne(query);
=======
		DBObject entity = MongoUtils.findCollection(this.config, this.client).findOne(query);
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
		this.log.debug("User for: " + username + " is " + entity);
		return entity != null ? entity.get("password").toString() : null;
	}
}
