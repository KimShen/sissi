package com.sissi.ucenter.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.VCardContext;
import com.sissi.ucenter.field.Field.FieldParser;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月10日
 */
public class MongoVCardContext extends MongoFieldContext implements VCardContext {

	private final Log log = LogFactory.getLog(this.getClass());

	private MongoConfig config;

	private Map<String, FieldParser<Object>> parser;

	public MongoVCardContext(MongoConfig config, Map<String, FieldParser<Object>> parser) {
		super();
		this.config = config;
		this.parser = parser;
	}

	@Override
	public VCardContext set(JID jid, Fields fields) {
		DBObject query = BasicDBObjectBuilder.start("username", jid.getUser()).get();
		DBObject entity = BasicDBObjectBuilder.start("$set", super.getEntities(fields, BasicDBObjectBuilder.start())).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		this.config.collection().update(query, entity);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Fields> T get(JID jid, T fields) {
		DBObject query = BasicDBObjectBuilder.start("username", jid.getUser()).get();
		this.log.debug("Query: " + query);
		Map<String, Object> entity = this.config.collection().findOne(query).toMap();
		for (String element : entity.keySet()) {
			if(this.parser.containsKey(element)){
				fields.add(this.parser.get(element).read(entity.get(element)));
			}
		}
		return fields;
	}
}
