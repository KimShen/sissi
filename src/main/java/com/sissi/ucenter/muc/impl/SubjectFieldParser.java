package com.sissi.ucenter.muc.impl;

import java.util.Map;

import com.sissi.config.MongoConfig;
import com.sissi.protocol.iq.vcard.field.Subject;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.FieldParser;

/**
 * @author kim 2013年12月12日
 */
public class SubjectFieldParser implements FieldParser<Map<String, Object>> {

	private final Subject subject = new Subject();

	@Override
	public Field<?> read(Map<String, Object> element) {
		Object subject = element.get(MongoConfig.FIELD_SUBJECT);
		return subject == null ? this.subject : new Subject(subject.toString());
	}

	public String support() {
		return MongoConfig.FIELD_SUBJECT;
	}
}
