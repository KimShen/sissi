package com.sissi.ucenter.access.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.ucenter.access.AuthAccessor;

/**
 * @author kim 2013-11-6
 */
public class MongoAuthAccessor implements AuthAccessor {

	private final DBObject filter = BasicDBObjectBuilder.start(MongoConfig.FIELD_PASSWORD, 1).get();

	private final MongoConfig config;

	public MongoAuthAccessor(MongoConfig config) {
		super();
		this.config = config;
	}

	/*
	 * {"username":Xxx},{"password":1}
	 * 
	 * @see com.sissi.ucenter.user.access.AuthAccessor#access(java.lang.String)
	 */
	@Override
	public String access(String username) {
		return MongoUtils.asString(this.config.collection().findOne(BasicDBObjectBuilder.start(Dictionary.FIELD_USERNAME, username).get(), this.filter), Dictionary.FIELD_PASSWORD);
	}
}
