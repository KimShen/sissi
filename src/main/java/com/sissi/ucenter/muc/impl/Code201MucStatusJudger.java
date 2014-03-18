package com.sissi.ucenter.muc.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月16日
 */
public class Code201MucStatusJudger implements MucStatusJudger {

	private final DBObject filter = BasicDBObjectBuilder.start(MongoConfig.FIELD_ACTIVATE, 1).get();

	private final MongoConfig config;

	public Code201MucStatusJudger(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public MucStatus judege(MucStatus status) {
		return status.owner() && !Extracter.asBoolean(this.config.collection().findOne(BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, status.group()).add(MongoConfig.FIELD_ACTIVATE, false).get(), this.filter), MongoConfig.FIELD_ACTIVATE, true) ? status.add("201") : status;
	}
}
