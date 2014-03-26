package com.sissi.ucenter.muc.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月16日
 */
public class Code201MucStatusJudger implements MucStatusJudger {

	private final DBObject query = BasicDBObjectBuilder.start(MongoConfig.FIELD_CONFIGS + "." + MongoConfig.FIELD_ACTIVATE, false).get();

	private final MongoConfig config;

	public Code201MucStatusJudger(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public MucStatus judege(MucStatus status) {
		return status.owner() && this.config.collection().findOne(BasicDBObjectBuilder.start(this.query.toMap()).add(MongoConfig.FIELD_JID, status.group()).get()) != null ? status.add("201") : status;
	}
}
