package com.sissi.ucenter.relation.muc.room.config;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigParser;

/**
 * @author kim 2014年5月6日
 */
public class PersistentRoomConfigParser implements RoomConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return Boolean.valueOf(field.getValue().toString());
	}

	public String field() {
		return Dictionary.FIELD_PERSISTENT;
	}

	@Override
	public String support() {
		return RoomConfig.PERSISTENTROOM.toString();
	}
}
