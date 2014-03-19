package com.sissi.ucenter.muc.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2014年3月19日
 */
public class MongoRelationMuc4RoleContext extends MongoRelationMucContext {

	public MongoRelationMuc4RoleContext(boolean activate, MongoConfig config, JIDBuilder jidBuilder) throws Exception {
		super(activate, config, jidBuilder);
	}

	@Override
	public MongoRelationMucContext update(JID from, JID to, String status) {
		super.config().collection().update(BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_ID, from.asString()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + ".$." + MongoConfig.FIELD_ROLE, status).get()).get(), true, false, WriteConcern.SAFE);
		return this;
	}
}
