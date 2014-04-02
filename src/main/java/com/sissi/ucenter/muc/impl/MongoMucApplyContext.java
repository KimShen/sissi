package com.sissi.ucenter.muc.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.impl.MongoFieldContext;
import com.sissi.ucenter.muc.MucApplyContext;

/**
 * @author kim 2014年3月12日
 */
public class MongoMucApplyContext extends MongoFieldContext implements MucApplyContext {

	private final MongoConfig config;

	public MongoMucApplyContext(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public boolean apply(JID from, JID to, Fields fields) {
		DBObject entity = super.getEntities(fields, BasicDBObjectBuilder.start());
		try {
			this.config.collection().update(BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, to.asStringWithBare()).add(MongoConfig.FIELD_INFORMATIONS + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(MongoConfig.FIELD_INFORMATIONS + ".$." + MongoConfig.FIELD_INFORMATION, entity).get()).get(), true, false, WriteConcern.SAFE);
		} catch (MongoException e) {
			this.config.collection().update(BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, to.asStringWithBare()).get(), BasicDBObjectBuilder.start().add("$addToSet", BasicDBObjectBuilder.start(MongoConfig.FIELD_INFORMATIONS, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_ACTIVATE, System.currentTimeMillis()).add(MongoConfig.FIELD_INFORMATION, entity).get()).get()).get());
		}
		return true;
	}
}
