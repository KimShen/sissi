package com.sissi.ucenter.muc.impl;

import java.util.Map;

import com.sissi.config.MongoConfig;
import com.sissi.protocol.iq.vcard.field.muc.Activate;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.FieldParser;

/**
 * @author kim 2013年12月12日
 */
public class MucActivateFieldParser implements FieldParser<Map<String, Object>> {

	@Override
	public Field<?> read(Map<String, Object> element) {
		return new Activate(element.get(MongoConfig.FIELD_ACTIVATE).toString());
	}

	public String support() {
		return MongoConfig.FIELD_ACTIVATE;
	}
}
