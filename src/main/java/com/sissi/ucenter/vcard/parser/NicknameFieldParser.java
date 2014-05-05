package com.sissi.ucenter.vcard.parser;

import com.sissi.field.Field;
import com.sissi.field.FieldParser;
import com.sissi.protocol.iq.vcard.field.Nickname;

/**
 * @author kim 2013年12月12日
 */
public class NicknameFieldParser implements FieldParser<String> {

	@Override
	public Field<?> read(String element) {
		return new Nickname(element);
	}
	
	public String support(){
		return Nickname.NAME;
	}
}
