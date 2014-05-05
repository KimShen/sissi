package com.sissi.ucenter.relation.muc.room.config;

import com.sissi.config.Dictionary;

/**
 * 主题(来自于Message)
 * 
 * @author kim 2014年3月25日
 */
public class SubjectMessageParser extends SubjectConfigParser {

	public String field() {
		return Dictionary.FIELD_SUBJECT;
	}

	public String support() {
		return Dictionary.FIELD_SUBJECT;
	}
}
