package com.sissi.ucenter.relation.muc.status.adder;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 等待激活
 * 
 * @author kim 2014年3月16日
 */
public class Code201StatusAdder implements CodeStatusAdder {

	/**
	 * {"configs.activate"}
	 */
	private final DBObject query = BasicDBObjectBuilder.start(Dictionary.FIELD_CONFIGS + "." + Dictionary.FIELD_ACTIVATE, false).get();

	private final MongoConfig config;

	public Code201StatusAdder(MongoConfig config) {
		super();
		this.config = config;
	}

	/*
	 * {"configs.activate","jid",Xxx}</p>TODO: Cached
	 * 
	 * @see com.sissi.ucenter.relation.muc.status.CodeStatusAdder#judege(com.sissi.ucenter.relation.muc.status.CodeStatus)
	 */
	@Override
	public CodeStatus add(CodeStatus status) {
		return status.loop() && this.config.collection().findOne(BasicDBObjectBuilder.start(this.query.toMap()).add(Dictionary.FIELD_JID, status.group()).get()) != null ? status.add("201") : status;
	}
}
