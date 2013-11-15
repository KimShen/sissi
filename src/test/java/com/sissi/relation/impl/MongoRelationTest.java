package com.sissi.relation.impl;

import junit.framework.Assert;

import org.bson.types.ObjectId;
import org.easymock.EasyMock;
import org.junit.Test;

import com.mongodb.DBObject;

/**
 * @author kim 2013-11-12
 */
public class MongoRelationTest {

	@Test
	public void testFillData() {
		DBObject db = EasyMock.createMock(DBObject.class);
		ObjectId _id = new ObjectId();
		EasyMock.expect(db.get("_id")).andReturn(_id).anyTimes();
		EasyMock.expect(db.get("jid")).andReturn("A").anyTimes();
		EasyMock.expect(db.get("name")).andReturn("B").anyTimes();
		EasyMock.expect(db.get("state")).andReturn("D").anyTimes();
		EasyMock.replay(db);
		MongoRelation relation = new MongoRelation(db);
		Assert.assertEquals(4, relation.toEntity().size());
		Assert.assertEquals(_id, relation.toEntity().get("_id"));
		Assert.assertEquals("A", relation.toEntity().get("jid"));
		Assert.assertEquals("B", relation.toEntity().get("name"));
		Assert.assertEquals("D", relation.toEntity().get("state"));
	}
}
