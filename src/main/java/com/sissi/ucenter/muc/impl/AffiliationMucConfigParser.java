package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.OwnerConfig;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.muc.MucConfigParser;

/**
 * @author kim 2014年3月25日
 */
public class AffiliationMucConfigParser implements MucConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return ItemAffiliation.parse(field.getValue().toString()).toString(true);
	}

	public String field() {
		return MongoConfig.FIELD_AFFILIATION;
	}

	@Override
	public String support() {
		return OwnerConfig.ROOMCONFIG_AFFILIATION.toString();
	}
}
