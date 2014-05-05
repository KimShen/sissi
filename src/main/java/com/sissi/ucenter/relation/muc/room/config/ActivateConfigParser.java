package com.sissi.ucenter.relation.muc.room.config;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.ucenter.relation.muc.room.RoomConfigParser;

/**
 * 激活
 * 
 * @author kim 2014年3月25日
 */
public class ActivateConfigParser implements RoomConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return Boolean.valueOf(field.getValue().toString());
	}

	/*
	 * Dictionary.FIELD_ACTIVATE
	 * 
	 * @see com.sissi.ucenter.relation.muc.room.ConfigParser#field()
	 */
	public String field() {
		return Dictionary.FIELD_ACTIVATE;
	}

	/*
	 * Dictionary.FIELD_ACTIVATE
	 * 
	 * @see com.sissi.ucenter.relation.muc.room.ConfigParser#support()
	 */
	@Override
	public String support() {
		return Dictionary.FIELD_ACTIVATE;
	}
}
