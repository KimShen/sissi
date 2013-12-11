package com.sissi.ucenter.impl;

import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.Field;
import com.sissi.ucenter.Field.Fields;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2013年12月10日
 */
public class MongoVCardContext implements VCardContext {

	private final Log log = LogFactory.getLog(this.getClass());

	private MongoConfig config;

	public MongoVCardContext(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public VCardContext push(JID jid, Fields fields) {
		DBObject query = BasicDBObjectBuilder.start("username", jid.getUser()).get();
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
		for (Field each : fields.getFields()) {
			builder.add(each.getName(), each.getValue());
		}
		DBObject entity = BasicDBObjectBuilder.start().add("$set", builder.get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		this.config.find().update(query, entity);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Fields> T pull(JID jid, T fields) {
		DBObject query = BasicDBObjectBuilder.start("username", jid.getUser()).get();
		this.log.debug("Query: " + query);
		for (Map.Entry<String, Object> param : (Set<Map.Entry<String, Object>>) this.config.find().findOne(query).toMap().entrySet()) {
			fields.addField(param.getKey(), param.getValue());
		}
		return fields;
	}
}
