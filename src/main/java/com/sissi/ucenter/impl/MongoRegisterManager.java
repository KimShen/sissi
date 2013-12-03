package com.sissi.ucenter.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.protocol.iq.login.Register.Field;
import com.sissi.ucenter.RegisterManager;

/**
 * @author kim 2013年12月3日
 */
public class MongoRegisterManager implements RegisterManager {

	private final Log log = LogFactory.getLog(this.getClass());

	private MongoConfig config;

	public MongoRegisterManager(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public Boolean register(List<Field> fields) {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
		for (Field field : fields) {
			builder.add(field.getName(), field.getText());
		}
		DBObject entity = builder.get();
		this.log.debug("Entity: " + entity);
		return this.config.find().save(entity, WriteConcern.SAFE).getN() > 0;
	}
}
