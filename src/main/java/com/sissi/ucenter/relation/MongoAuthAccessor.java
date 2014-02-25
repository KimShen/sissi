package com.sissi.ucenter.relation;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.commons.Extracter;
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
		return Extracter.asString(this.config.collection().findOne(BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_USERNAME, username).get()), this.password);
	}
}
