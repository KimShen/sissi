package com.sissi.ucenter.relation.muc.apply.register;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.field.Fields;
import com.sissi.ucenter.impl.MongoFieldsContext;
import com.sissi.ucenter.relation.muc.apply.ApplyContext;

/**
 * 更新个人信息请求(Information). 索引策略1: {"jid":1}</p>索引策略2: {"jid":1,"informations.jid":1}
 * 
 * @author kim 2014年3月12日
 */
public class MongoRegisterSubmitContext extends MongoFieldsContext implements ApplyContext {

	private final MongoConfig config;

	public MongoRegisterSubmitContext(MongoConfig config) {
		super();
		this.config = config;
	}

	/*
	 * 全量更新
	 * 
	 * @see com.sissi.ucenter.relation.muc.request.RequestContext#request(com.sissi.context.JID, com.sissi.context.JID, com.sissi.field.Fields)
	 */
	@Override
	public boolean apply(JID invoker, JID group, Fields fields) {
		DBObject entity = super.entities(fields, BasicDBObjectBuilder.start());
		try {
			// {"jid":to.bare,"informations.jid":from.bare},{"$set":{"infomrations.$.information":..entity..}}
			this.config.collection().update(BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, group.asStringWithBare()).add(Dictionary.FIELD_INFORMATIONS + "." + Dictionary.FIELD_JID, invoker.asStringWithBare()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(Dictionary.FIELD_INFORMATIONS + ".$." + Dictionary.FIELD_INFORMATION, entity).get()).get(), true, false, WriteConcern.SAFE);
		} catch (MongoException e) {
			// {"jid":to.bare},{"$addToSet"{"informations":{"jid":from.bare,"activate":当前时间,"information":...entity...}}}
			this.config.collection().update(BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, group.asStringWithBare()).get(), BasicDBObjectBuilder.start().add("$addToSet", BasicDBObjectBuilder.start(Dictionary.FIELD_INFORMATIONS, BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, invoker.asStringWithBare()).add(Dictionary.FIELD_ACTIVATE, System.currentTimeMillis()).add(Dictionary.FIELD_INFORMATION, entity).get()).get()).get());
		}
		return true;
	}
}
