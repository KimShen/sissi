package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.protocol.muc.OwnerConfig;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.muc.MucConfigParser;

/**
 * @author kim 2014年3月25日
 */
public class SubjectMucConfigParser implements MucConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return field.getValue().toString();
	}

	public String field() {
		return MongoConfig.FIELD_SUBJECT;
	}

	@Override
	public String support() {
		return OwnerConfig.ROOMCONFIG_SUBJECT.toString();
	}
}
