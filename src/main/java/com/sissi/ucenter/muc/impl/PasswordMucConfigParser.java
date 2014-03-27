package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.protocol.muc.OwnerConfig;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.muc.MucConfigParser;

/**
 * @author kim 2014年3月25日
 */
public class PasswordMucConfigParser implements MucConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return field.getValue();
	}

	public String field() {
		return MongoConfig.FIELD_PASSWORD;
	}

	@Override
	public String support() {
		return OwnerConfig.ROOMCONFIG_ROOMSECRET.toString();
	}
}
