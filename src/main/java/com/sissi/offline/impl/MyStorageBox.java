package com.sissi.offline.impl;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.sissi.context.JID;
import com.sissi.offline.StorageBlock;
import com.sissi.offline.StorageBox;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-15
 */
public class MyStorageBox implements StorageBox {

	private String db;

	private String collection;

	private MongoClient mongoClient;

	private List<StorageBlock> storageBlocks;

	public MyStorageBox(String db, String collection, MongoClient mongoClient, List<StorageBlock> storageBlocks) {
		super();
		this.db = db;
		this.collection = collection;
		this.storageBlocks = storageBlocks;
		this.mongoClient = mongoClient;
	}

	private DBCollection findCollection() {
		return this.mongoClient.getDB(this.db).getCollection(this.collection);
	}

	@Override
	public List<Protocol> open(JID jid) {
		return new Protocols(this.findCollection().find(new BasicDBObject("jid", jid.asStringWithBare())));
	}

	@Override
	public void close(Protocol protocol) {
		for (StorageBlock blocks : this.storageBlocks) {
			if (blocks.isSupport(protocol)) {
				this.findCollection().save(new BasicDBObject(blocks.write(protocol)));
			}
		}
	}

	private class Protocols extends ArrayList<Protocol> {

		private static final long serialVersionUID = 1L;

		public Protocols(DBCursor cursor) {
			super();
			while (cursor.hasNext()) {
				BasicDBObject each = (BasicDBObject) cursor.next();
				for (StorageBlock blocks : MyStorageBox.this.storageBlocks) {
					if (blocks.isSupport(each)) {
						this.add(blocks.read(each));
					}
				}
			}
		}

	}
}
