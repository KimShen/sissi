package com.sissi.ucenter.muc.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
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
	public boolean apply(Fields fields) {
		try {
			return this.config.collection().save(super.getEntities(fields, BasicDBObjectBuilder.start()), WriteConcern.SAFE).getError() == null ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
