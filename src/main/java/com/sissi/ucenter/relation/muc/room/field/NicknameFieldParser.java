package com.sissi.ucenter.relation.muc.room.field;

import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.field.FieldParser;
import com.sissi.protocol.iq.data.XNickname;

/**
 * 房间名称
 * 
 * @author kim 2013年12月12日
 */
public class NicknameFieldParser implements FieldParser<Map<String, Object>> {

	private final XNickname empty = new XNickname();

	@Override
	public Field<?> read(Map<String, Object> element) {
		Object subject = element.get(Dictionary.FIELD_SUBJECT);
		return subject == null ? this.empty : new XNickname(subject.toString());
	}

	public String support() {
		return Dictionary.FIELD_SUBJECT;
	}
}
