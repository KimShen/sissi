package com.sissi.ucenter.relation;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoProxyConfig;
import com.sissi.ucenter.AuthAccessor;

/**
 * @author kim 2013-11-6
 */
public class MongoAuthAccessor implements AuthAccessor {

	private final String password = "password";

	private final MongoConfig config;

	public MongoAuthAccessor(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public String access(String username) {
		DBObject entity = this.config.collection().findOne(BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_USERNAME, username).get());
		return entity != null ? entity.get(this.password).toString() : null;
	}
}
