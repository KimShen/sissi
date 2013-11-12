package com.sissi.relation.impl;

import java.util.HashSet;

import com.mongodb.DBCursor;
import com.sissi.relation.Relation;

/**
 * @author kim 2013-11-6
 */
public class MongoRelations extends HashSet<Relation> {

	private static final long serialVersionUID = 1L;

	public MongoRelations(DBCursor cursor) {
		if (cursor == null) {
			return;
		}
		while (cursor.hasNext()) {
			this.add(new MongoRelation(cursor.next()));
		}
	}
}
