package com.sissi.ucenter.relation.muc.room.config;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.ucenter.relation.muc.room.RoomConfigParser;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 真实JID
 * 
 * @author kim 2014年3月25日
 */
public class WhoisConfigParser implements RoomConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return Boolean.valueOf(field.getValue().toString());
	}

	public String field() {
		return Dictionary.FIELD_WHOIS;
	}

	@Override
	public String support() {
		return RoomConfig.WHOIS.toString();
	}
}
