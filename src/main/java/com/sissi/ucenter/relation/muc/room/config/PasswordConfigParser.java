package com.sissi.ucenter.relation.muc.room.config;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.ucenter.relation.muc.room.RoomConfigParser;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 密码
 * 
 * @author kim 2014年3月25日
 */
public class PasswordConfigParser implements RoomConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return field.getValue();
	}

	public String field() {
		return Dictionary.FIELD_PASSWORD;
	}

	@Override
	public String support() {
		return RoomConfig.ROOMSECRET.toString();
	}
}
