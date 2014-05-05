package com.sissi.ucenter.relation.muc.room.config;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.muc.room.RoomConfigParser;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 岗位
 * 
 * @author kim 2014年3月25日
 */
public class AffiliationConfigParser implements RoomConfigParser {

	@Override
	public Object parse(Field<?> field) {
		return ItemAffiliation.parse(field.getValue().toString()).toString(true);
	}

	public String field() {
		return Dictionary.FIELD_AFFILIATION;
	}

	@Override
	public String support() {
		return RoomConfig.AFFILIATION.toString();
	}
}
