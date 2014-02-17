package com.sissi.ucenter.user;

import com.sissi.protocol.iq.vcard.field.Nickname;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.FieldParser;

/**
 * @author kim 2013年12月12日
 */
public class NicknameFieldParser implements FieldParser<String> {

	@Override
	public Field<?> read(String element) {
		return new Nickname(element);
	}
}
