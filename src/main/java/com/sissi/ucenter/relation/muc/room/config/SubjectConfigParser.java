package com.sissi.ucenter.relation.muc.room.config;

import com.sissi.field.Field;
import com.sissi.ucenter.relation.muc.room.RoomConfigParser;

/**
 * @author kim 2014年4月24日
 */
abstract class SubjectConfigParser implements RoomConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return field.getValue() != null ? field.getValue().toString() : null;
	}
}
