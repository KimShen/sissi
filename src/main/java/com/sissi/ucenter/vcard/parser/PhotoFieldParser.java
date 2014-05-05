package com.sissi.ucenter.vcard.parser;

import java.util.Map;

import com.sissi.field.Field;
import com.sissi.field.FieldParser;
import com.sissi.protocol.iq.vcard.field.Binval;
import com.sissi.protocol.iq.vcard.field.Photo;
import com.sissi.protocol.iq.vcard.field.Type;

/**
 * @author kim 2013年12月12日
 */
public class PhotoFieldParser implements FieldParser<Map<String, Object>> {

	@Override
	public Field<?> read(Map<String, Object> element) {
		return new Photo(element.get(Type.NAME).toString(), element.get(Binval.NAME).toString());
	}

	public String support(){
		return Photo.NAME;
	}
}
