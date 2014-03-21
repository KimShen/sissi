package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.muc.MucAffiliationBuilder;

/**
 * @author kim 2014年3月19日
 */
public class MongoRelationMuc4AffiliationContext extends MongoRelationMucContext {

	private final MucAffiliationBuilder mucAffiliationBuilder;

	private final boolean cascade;

	public MongoRelationMuc4AffiliationContext(boolean activate, boolean cascade, MongoConfig config, JIDBuilder jidBuilder, MucAffiliationBuilder mucAffiliationBuilder) throws Exception {
		super(activate, config, jidBuilder);
		this.mucAffiliationBuilder = mucAffiliationBuilder;
		this.cascade = cascade;
	}

	@Override
	public MongoRelationMucContext update(JID from, JID to, String status) {
		this.mucAffiliationBuilder.build(to).approve(from, status);
		if (this.cascade && (ItemAffiliation.OUTCAST.equals(status) || !ItemAffiliation.parse(status).contains(super.mucConfigBuilder.build(to).pull(MongoConfig.FIELD_AFFILIATION, String.class)))) {
			super.remove(from, to, true);
		}
		return this;
	}
}
