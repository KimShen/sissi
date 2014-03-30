package com.sissi.ucenter.muc.impl;

import java.util.Map;

import com.sissi.config.MongoConfig;
import com.sissi.protocol.iq.vcard.field.Nickname;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.FieldParser;

/**
 * @author kim 2013年12月12日
 */
public class NicknameFieldParser implements FieldParser<Map<String, Object>> {

	private final Nickname empty = new Nickname();

	@Override
	public Field<?> read(Map<String, Object> element) {
		Object subject = element.get(MongoConfig.FIELD_SUBJECT);
		return subject == null ? this.empty : new Nickname(subject.toString());
	}

	public String support() {
		return MongoConfig.FIELD_SUBJECT;
	}
}
