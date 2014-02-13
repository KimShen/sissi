package com.sissi.ucenter.relation;

import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JIDBuilder;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年2月11日
 */
public class MongoRelationMucContext extends MongoRelationContext {

	public MongoRelationMucContext(MongoConfig config, JIDBuilder jidBuilder) {
		super(config, jidBuilder);
	}

	@Override
	protected Relation build(DBObject db) {
		return null;
	}
}
