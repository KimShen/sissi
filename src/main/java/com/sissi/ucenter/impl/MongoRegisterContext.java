package com.sissi.ucenter.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.RegisterContext;

/**
 * @author kim 2013年12月3日
 */
public class MongoRegisterContext implements RegisterContext {

	private final Log log = LogFactory.getLog(this.getClass());

	private MongoConfig config;

	public MongoRegisterContext(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public Boolean register(Fields fields) {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
		for (Field field : fields) {
			builder.add(field.getName(), field.getText());
		}
		DBObject entity = builder.get();
		this.log.debug("Entity: " + entity);
		try {
			this.config.find().save(entity, WriteConcern.SAFE);
			return true;
		} catch (MongoException e) {
			return false;
		}
	}
}
