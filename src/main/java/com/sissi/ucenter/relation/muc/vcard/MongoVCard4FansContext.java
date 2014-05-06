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
import com.sissi.ucenter.impl.MongoFieldsContext;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * MUC JID VCard.索引策略1: {"affiliations.jid":1}</p>索引策略2: {"informations.jid":1}</p>索引策略3: {"jid":1}
 * 
 * @author kim 2013年12月10日
 */
public class MongoVCard4FansContext extends MongoFieldsContext implements VCardContext {

	private final Map<String, FieldParser<Object>> parser = new HashMap<String, FieldParser<Object>>();

	/**
	 * {"$unwind":"$informations"}
	 */
	private final DBObject unwind = BasicDBObjectBuilder.start("$unwind", "$" + Dictionary.FIELD_INFORMATIONS).get();

	/**
	 * {"$project":{"activate":"$informations.activate","information":"$informations.information"}}
	 */
	private final DBObject project = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ACTIVATE, "$" + Dictionary.FIELD_INFORMATIONS + "." + Dictionary.FIELD_ACTIVATE).add(Dictionary.FIELD_INFORMATION, "$" + Dictionary.FIELD_INFORMATIONS + "." + Dictionary.FIELD_INFORMATION).get()).get();

	private final MongoConfig config;

	/**
	 * ucenter.vcard.user
	 */
	private final VCardContext proxy;

	private final JIDBuilder jidBuilder;

	public MongoVCard4FansContext(MongoConfig config, VCardContext proxy, JIDBuilder jidBuilder, List<FieldParser<Object>> parser) {
		super();
		this.proxy = proxy;
		this.config = config;
		this.jidBuilder = jidBuilder;
		for (FieldParser<Object> each : parser) {
			this.parser.put(each.support(), each);
		}
	}

	public boolean exists(String jid) {
		return this.exists(this.jidBuilder.build(jid));
	}

	/*
	 * 房间岗位中是否存在该JID
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#exists(com.sissi.context.JID)
	 */
	public boolean exists(JID jid) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_JID, jid.asStringWithBare()).get()) != null;
	}

	public VCardContext push(JID jid, Field<String> field) {
		return this;
	}

	@Override
	public VCardContext push(JID jid, Fields fields) {
		return this;
	}

	/*
	 * Not Support
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#get(com.sissi.context.JID, java.lang.String)
	 */
	public Field<String> pull(JID jid, String name) {
		return null;
	}

	/*
	 * Not Support
	 * 
	 * @see com.sissi.ucenter.vcard.VCardContext#get(com.sissi.context.JID, java.lang.String, java.lang.String)
	 */
	public Field<String> pull(JID jid, String name, String def) {
		return null;
	}

	@Override
	public <T extends Fields> T pull(JID jid, T fields) {
		// ucenter.vcard.user获取个人信息
		this.proxy.pull(this.jidBuilder.build(jid.resource()), fields);
		// 激活时间和个人信息{"$match":{"jid":jid.bare},{"$unwind":"$informations"},{"$match":{"informations.jid":jid.resource},{"$project":{"activate":"$informations.activate","information" :"$informations.information"}}
		List<?> vcards = MongoUtils.asList(this.config.collection().aggregate(BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_JID, jid.asStringWithBare()).get()).get(), this.unwind, BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_INFORMATIONS + "." + Dictionary.FIELD_JID, jid.resource()).get()).get(), this.project).getCommandResult(), Dictionary.FIELD_RESULT);
		Map<String, Object> entity = MongoUtils.asMap(vcards.isEmpty() ? null : DBObject.class.cast(vcards.get(0)));
		for (String element : entity.keySet()) {
			if (this.parser.containsKey(element)) {
				fields.add(this.parser.get(element).read(entity));
			}
		}
		return fields;
	}
}
