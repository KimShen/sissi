package com.sissi.ucenter.user;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoException;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月3日
 */
public class MongoRegisterContext extends MongoFieldContext implements RegisterContext {

	private final MongoConfig config;

	public MongoRegisterContext(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public Boolean register(Fields fields) {
		try {
			return this.config.collection().save(super.getEntities(fields, BasicDBObjectBuilder.start())).getError() == null;
		} catch (MongoException e) {
			return false;
		}
	}
}
