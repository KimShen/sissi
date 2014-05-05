package com.sissi.ucenter.relation.muc.room.config;

import com.sissi.config.Dictionary;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 主题(来自于MUC#owner)
 * 
 * @author kim 2014年3月25日
 */
public class SubjectOwnerParser extends SubjectConfigParser {

	public String field() {
		return Dictionary.FIELD_SUBJECT;
	}

	@Override
	public String support() {
		return RoomConfig.ROOMNAME.toString();
	}
}
