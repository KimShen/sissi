package com.sissi.ucenter.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月3日
 */
public class MongoRegisterContext extends MongoFieldContext implements RegisterContext {

	private final Log log = LogFactory.getLog(this.getClass());

	private MongoConfig config;

	public MongoRegisterContext(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public Boolean register(Fields fields) {
		DBObject entity = super.getEntities(fields, BasicDBObjectBuilder.start());
		this.log.debug("Entity: " + entity);
		try {
			this.config.find().save(entity, WriteConcern.SAFE);
			return true;
		} catch (MongoException e) {
			return false;
		}
	}
}
