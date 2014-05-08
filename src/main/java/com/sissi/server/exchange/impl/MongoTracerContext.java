package com.sissi.server.exchange.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.server.exchange.Tracer;
import com.sissi.server.exchange.TracerContext;

/**
 * @author kim 2014年5月8日
 */
public class MongoTracerContext implements TracerContext {

	private final DBObject failed = BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(Dictionary.FIELD_ACTIVATE, false).get()).get();

	private final MongoConfig config;

	public MongoTracerContext(MongoConfig config) {

		super();
		this.config = config;
	}

	@Override
	public boolean trace(Tracer tracer) {
		return MongoUtils.effect(this.config.collection().save(BasicDBObjectBuilder.start(tracer.plus()).add(Dictionary.FIELD_PID, tracer.id()).add(Dictionary.FIELD_FROM, tracer.initiator()).add(Dictionary.FIELD_ACTIVATE, true).add(Dictionary.FIELD_TO, tracer.target()).add(Dictionary.FIELD_TIMESTAMP, System.currentTimeMillis()).get(), WriteConcern.SAFE));
	}

	public boolean trace(String id) {
		return MongoUtils.asBoolean(this.config.collection().findAndModify(BasicDBObjectBuilder.start().add(Dictionary.FIELD_PID, id).get(), this.failed), Dictionary.FIELD_ACTIVATE, false);
	}
}
