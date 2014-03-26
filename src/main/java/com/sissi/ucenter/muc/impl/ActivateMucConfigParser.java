package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.muc.MucConfigParser;

/**
 * @author kim 2014年3月25日
 */
public class ActivateMucConfigParser implements MucConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return Boolean.valueOf(field.getValue().toString());
	}

	public String field() {
		return MongoConfig.FIELD_ACTIVATE;
	}

	@Override
	public String support() {
		return MongoConfig.FIELD_ACTIVATE;
	}
}
