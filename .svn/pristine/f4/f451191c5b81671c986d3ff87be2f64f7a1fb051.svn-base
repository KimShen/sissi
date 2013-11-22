package com.sissi.pipeline.in.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.config.Config;
import com.sissi.pipeline.in.auth.AuthAccessor;
import com.sissi.pipeline.in.auth.AuthCertificate;
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
	public Boolean access(AuthCertificate user) {
		DBObject query = BasicDBObjectBuilder.start().add("username", user.getUser()).add("password", user.getPass()).get();
		this.log.debug("Query: " + query);
		long count = MongoUtils.findCollection(this.config, this.client).count(query);
		this.logIfDuplication(user, count);
		return count > 0;
	}

	private void logIfDuplication(AuthCertificate user, long count) {
		if (count > 1) {
			this.log.fatal("Duplicated account for: " + user.getUser());
		}
	}
}
