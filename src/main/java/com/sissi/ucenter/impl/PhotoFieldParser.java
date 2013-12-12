package com.sissi.ucenter.impl;

import java.util.Map;

import com.sissi.protocol.iq.vcard.field.Binval;
import com.sissi.protocol.iq.vcard.field.Photo;
import com.sissi.protocol.iq.vcard.field.Type;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.FieldParser;

/**
 * @author kim 2013年12月12日
 */
public class PhotoFieldParser implements FieldParser<Map<String, Object>> {

	@Override
	public Field<?> read(Map<String, Object> element) {
		return new Photo(element.get(Type.NAME).toString(), element.get(Binval.NAME).toString());
	}

}
