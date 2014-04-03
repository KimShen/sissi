package com.sissi.ucenter.muc.impl;

import java.util.Map;

import com.sissi.config.MongoConfig;
import com.sissi.protocol.iq.data.XInput;
import com.sissi.protocol.muc.MucDomain;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.FieldParser;

/**
 * @author kim 2013年12月12日
 */
public class MucOnlineFieldParser implements FieldParser<Map<String, Object>> {

	@Override
	public Field<?> read(Map<String, Object> element) {
		Object count = element.get(MongoConfig.FIELD_COUNT);
		return new XInput(null, MongoConfig.FIELD_COUNT, MucDomain.ROOMINFO_OCCUPANTS.toString(), count == null ? "0" : count.toString());
	}

	public String support() {
		return MongoConfig.FIELD_COUNT;
	}
}
