package com.sissi.ucenter.muc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.FieldParser;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanField;
import com.sissi.ucenter.impl.MongoFieldContext;
import com.sissi.ucenter.user.VCardContext;

/**
 * @author kim 2013年12月10日
 */
public class MongoVCardContext extends MongoFieldContext implements VCardContext {

	private final Map<String, FieldParser<Object>> parser = new HashMap<String, FieldParser<Object>>();

	private final DBObject aggregateUnwind = BasicDBObjectBuilder.start("$unwind", "$" + MongoConfig.FIELD_INFORMATIONS).get();

	private final DBObject aggregateProject = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ACTIVATE, "$" + MongoConfig.FIELD_INFORMATIONS + "." + MongoConfig.FIELD_ACTIVATE).add(MongoConfig.FIELD_INFORMATION, "$" + MongoConfig.FIELD_INFORMATIONS + "." + MongoConfig.FIELD_INFORMATION).get()).get();

	private final MongoConfig config;

	private final VCardContext proxy;

	private final JIDBuilder jidBuilder;

	public MongoVCardContext(MongoConfig config, VCardContext proxy, JIDBuilder jidBuilder, List<FieldParser<Object>> parser) {
		super();
		this.proxy = proxy;
		this.config = config;
		this.jidBuilder = jidBuilder;
		for (FieldParser<Object> each : parser) {
			this.parser.put(each.support(), each);
		}
	}

	private DBObject buildQuery(JID jid) {
		return this.buildQuery(jid.asStringWithBare());
	}

	private DBObject buildQuery(String jid) {
		return BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, jid).get();
	}

	public boolean exists(String jid) {
		return this.exists(this.jidBuilder.build(jid));
	}

	public boolean exists(JID jid) {
		return this.config.collection().findOne(this.buildQuery(jid)) != null;
	}

	public VCardContext set(JID jid, Field<String> field) {
		return this;
	}

	@Override
	public VCardContext set(JID jid, Fields fields) {
		return this;
	}

	public Field<String> get(JID jid, String name) {
		return new BeanField<String>().setName(name).setValue(null);
	}

	@Override
	public <T extends Fields> T get(JID jid, T fields) {
		this.proxy.get(this.jidBuilder.build(jid.resource()), fields);
		List<?> vards = Extracter.asList(this.config.collection().aggregate(BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, jid.asStringWithBare()).get()).get(), this.aggregateUnwind, BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_INFORMATIONS + "." + MongoConfig.FIELD_JID, jid.resource()).get()).get(), this.aggregateProject).getCommandResult(), MongoConfig.FIELD_RESULT);
		Map<String, Object> entity = Extracter.asMap(vards.isEmpty() ? null : DBObject.class.cast(vards.get(0)));
		for (String element : entity.keySet()) {
			if (this.parser.containsKey(element)) {
				fields.add(this.parser.get(element).read(entity));
			}
		}
		return fields;
	}
}
