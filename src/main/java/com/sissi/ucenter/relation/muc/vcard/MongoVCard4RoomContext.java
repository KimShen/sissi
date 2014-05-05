package com.sissi.ucenter.relation.muc.vcard;

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
 * MUC房间 VCard. 索引策略:{"jid":1}
 * 
 * @author kim 2013年12月10日
 */
public class MongoVCard4RoomContext extends MongoFieldsContext implements VCardContext {

	private final Map<String, FieldParser<Object>> parser = new HashMap<String, FieldParser<Object>>();

	/**
	 * {"configs":1}
	 */
	private final DBObject filter = BasicDBObjectBuilder.start(Dictionary.FIELD_CONFIGS, 1).get();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoVCard4RoomContext(MongoConfig config, JIDBuilder jidBuilder, List<FieldParser<Object>> parser) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
		for (FieldParser<Object> each : parser) {
			this.parser.put(each.support(), each);
		}
	}

	/**
	 * {"jid":jid.bare}
	 * 
	 * @param jid
	 * @return
	 */
	private DBObject buildQuery(JID jid) {
		return BasicDBObjectBuilder.start(Dictionary.FIELD_JID, jid.asStringWithBare()).get();
	}

	/*
	 * 是否存在该岗位
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#exists(java.lang.String)
	 */
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

	/*
	 * 获取Configs中指定配置{"jid":jid.bare},{"configs.Xxx":1}
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#get(com.sissi.context.JID, java.lang.String)
	 */
	public Field<String> get(JID jid, String name) {
		return new BeanField<String>().name(name).value(MongoUtils.asString(MongoUtils.asDBObject(this.config.collection().findOne(this.buildQuery(jid), BasicDBObjectBuilder.start(Dictionary.FIELD_CONFIGS + "." + name, 1).get()), Dictionary.FIELD_CONFIGS), name));
	}

	public Field<String> get(JID jid, String name, String def) {
		Field<String> value = this.get(jid, name);
		return value.getValue() != null ? value : new BeanField<String>().name(name).value(def);
	}

	/*
	 * {"jid":jid.bare},{"configs":1}
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#get(com.sissi.context.JID, com.sissi.field.Fields)
	 */
	@Override
	public <T extends Fields> T get(JID jid, T fields) {
		Map<String, Object> entity = MongoUtils.asMap(MongoUtils.asDBObject(this.config.collection().findOne(this.buildQuery(jid), this.filter), Dictionary.FIELD_CONFIGS));
		for (String element : entity.keySet()) {
			if (this.parser.containsKey(element)) {
				fields.add(this.parser.get(element).read(entity));
			}
		}
		return fields;
	}
}
