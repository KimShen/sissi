package com.sissi.ucenter.vcard.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.field.Field;
import com.sissi.field.FieldParser;
import com.sissi.field.Fields;
import com.sissi.field.impl.BeanField;
import com.sissi.ucenter.impl.MongoFieldsContext;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 索引策略:{"username":1}
 * 
 * @author kim 2013年12月10日
 */
public class MongoVCardContext extends MongoFieldsContext implements VCardContext {

	/**
	 * 字段解析器
	 */
	private final Map<String, FieldParser<Object>> parser = new HashMap<String, FieldParser<Object>>();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoVCardContext(MongoConfig config, JIDBuilder jidBuilder, List<FieldParser<Object>> parser) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
		for (FieldParser<Object> each : parser) {
			this.parser.put(each.support(), each);
		}
	}

	/**
	 * {"username":Xxx}
	 * 
	 * @param jid
	 * @return
	 */
	private DBObject buildQuery(JID jid) {
		return BasicDBObjectBuilder.start(Dictionary.FIELD_USERNAME, jid.user()).get();
	}

	public boolean exists(JID jid) {
		return this.config.collection().findOne(this.buildQuery(jid)) != null;
	}

	public boolean exists(String jid) {
		return this.exists(this.jidBuilder.build(jid));
	}

	/*
	 * {"username":Xxx},{"$set":super.getEntity(...)}
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#set(com.sissi.context.JID, com.sissi.field.Field)
	 */
	public VCardContext push(JID jid, Field<String> field) {
		this.config.collection().update(this.buildQuery(jid), BasicDBObjectBuilder.start("$set", super.entity(field, BasicDBObjectBuilder.start())).get());
		return this;
	}

	@Override
	public VCardContext push(JID jid, Fields fields) {
		this.config.collection().update(this.buildQuery(jid), BasicDBObjectBuilder.start("$set", super.entities(fields, BasicDBObjectBuilder.start())).get());
		return this;
	}

	/*
	 * {"username":Xxx},{参数name:1}
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#get(com.sissi.context.JID, java.lang.String)
	 */
	public Field<String> pull(JID jid, String name) {
		return new BeanField<String>().name(name).value(MongoUtils.asString(this.config.collection().findOne(this.buildQuery(jid), BasicDBObjectBuilder.start(name, 1).get()), name));
	}

	public Field<String> pull(JID jid, String name, String def) {
		Field<String> value = this.pull(jid, name);
		return value.getValue() != null ? value : new BeanField<String>().value(def);
	}

	/*
	 * FieldParser相关
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#get(com.sissi.context.JID, com.sissi.field.Fields)
	 */
	@Override
	public <T extends Fields> T pull(JID jid, T fields) {
		Map<String, Object> entity = MongoUtils.asMap(this.config.collection().findOne(this.buildQuery(jid)));
		for (String element : entity.keySet()) {
			if (this.parser.containsKey(element)) {
				fields.add(this.parser.get(element).read(entity.get(element)));
			}
		}
		return fields;
	}
}
