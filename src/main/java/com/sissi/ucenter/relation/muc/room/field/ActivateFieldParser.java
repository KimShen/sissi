package com.sissi.ucenter.relation.muc.room.field;

import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.field.FieldParser;
import com.sissi.protocol.iq.vcard.field.muc.Activate;

/**
 * 激活
 * 
 * @author kim 2013年12月12日
 */
public class ActivateFieldParser implements FieldParser<Map<String, Object>> {

	@Override
	public Field<?> read(Map<String, Object> element) {
		return new Activate(element.get(Dictionary.FIELD_ACTIVATE).toString());
	}

	public String support() {
		return Dictionary.FIELD_ACTIVATE;
	}
}
