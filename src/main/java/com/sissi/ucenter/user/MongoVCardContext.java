package com.sissi.ucenter.user;

import java.util.Collections;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoProxyConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.VCardContext;
import com.sissi.ucenter.field.FieldParser;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月10日
 */
public class MongoVCardContext extends MongoFieldContext implements VCardContext {

	private final MongoConfig config;

	private final Map<String, FieldParser<Object>> parser;

	public MongoVCardContext(MongoConfig config, Map<String, FieldParser<Object>> parser) {
		super();
		this.config = config;
		this.parser = Collections.unmodifiableMap(parser);
	}

	public Boolean exists(JID jid) {
		return this.config.collection().findOne(this.buildQuery(jid)) != null;
	}

	@Override
	public VCardContext set(JID jid, Fields fields) {
		this.config.collection().update(this.buildQuery(jid), BasicDBObjectBuilder.start("$set", super.getEntities(fields, BasicDBObjectBuilder.start())).get());
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Fields> T get(JID jid, T fields) {
		Map<String, Object> entity = this.config.collection().findOne(this.buildQuery(jid)).toMap();
		for (String element : entity.keySet()) {
			if (this.parser.containsKey(element)) {
				fields.add(this.parser.get(element).read(entity.get(element)));
			}
		}
		return fields;
	}

	private DBObject buildQuery(JID jid) {
		return BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_USERNAME, jid.getUser()).get();
	}
}
