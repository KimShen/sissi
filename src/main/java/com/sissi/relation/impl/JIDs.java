package com.sissi.relation.impl;

import java.util.HashSet;

import com.mongodb.DBCursor;

/**
 * @author kim 2013-11-6
 */
public class JIDs extends HashSet<String> {

	private static final long serialVersionUID = 1L;

	public JIDs(DBCursor cursor, String key) {
		if (cursor == null) {
			return;
		}
		while (cursor.hasNext()) {
			this.add(cursor.next().get(key).toString());
		}
	}
}
