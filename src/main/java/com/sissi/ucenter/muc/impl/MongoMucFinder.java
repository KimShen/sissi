package com.sissi.ucenter.muc.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.muc.MucFinder;

/**
 * @author kim 2014年3月11日
 */
public class MongoMucFinder implements MucFinder {

	private final MongoConfig config;

	public MongoMucFinder(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public boolean exists(JID group) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get()) != null;
	}
}
