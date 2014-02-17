package com.sissi.context.impl;

import java.util.HashSet;

import com.mongodb.DBCursor;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2014年2月17日
 */
public class MongoJIDGroup extends HashSet<JID> {

	private final static long serialVersionUID = 1L;

	public MongoJIDGroup(JIDBuilder jidBuilder, DBCursor cursor, String key) {
		if (cursor == null) {
			return;
		}
		while (cursor.hasNext()) {
			this.add(jidBuilder.build(cursor.next().get(key).toString()));
		}
	}
}