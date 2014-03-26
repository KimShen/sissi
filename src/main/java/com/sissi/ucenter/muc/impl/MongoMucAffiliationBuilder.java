package com.sissi.ucenter.muc.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.muc.MucAffiliation;
import com.sissi.ucenter.muc.MucAffiliationBuilder;

/**
 * @author kim 2014年3月9日
 */
public class MongoMucAffiliationBuilder implements MucAffiliationBuilder {

	private final MongoConfig config;

	public MongoMucAffiliationBuilder(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public MucAffiliation build(JID group) {
		return new MucAffiliationImpl(group);
	}

	private final class MucAffiliationImpl implements MucAffiliation {

		private final JID group;

		public MucAffiliationImpl(JID group) {
			super();
			this.group = group;
		}

		@Override
		public MucAffiliation approve(JID jid, String affiliation) {
			try {
				MongoMucAffiliationBuilder.this.config.collection().update(BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, this.group.asStringWithBare()).add(MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_JID, jid.asStringWithBare()).get(), BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATIONS + ".$." + MongoConfig.FIELD_AFFILIATION, affiliation).get()).get(), true, false, WriteConcern.SAFE);
			} catch (MongoException e) {
				MongoMucAffiliationBuilder.this.config.collection().update(BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, this.group.asStringWithBare()).get(), BasicDBObjectBuilder.start("$addToSet", BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATIONS, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, jid.asStringWithBare()).add(MongoConfig.FIELD_AFFILIATION, affiliation).get()).get()).get(), true, false, WriteConcern.SAFE);
			}
			return this;
		}
	}
}
