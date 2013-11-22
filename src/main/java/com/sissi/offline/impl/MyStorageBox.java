package com.sissi.offline.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.config.Config;
import com.sissi.context.JID;
import com.sissi.offline.StorageBlock;
import com.sissi.offline.StorageBox;
import com.sissi.protocol.Protocol;
import com.sissi.util.MongoUtils;

/**
 * @author kim 2013-11-15
 */
public class MyStorageBox implements StorageBox {

	private Log log = LogFactory.getLog(this.getClass());

	private Config config;

	private MongoClient client;

	private List<StorageBlock> storageBlocks;

	public MyStorageBox(Config config, MongoClient mongoClient, List<StorageBlock> storageBlocks) {
		super();
		this.config = config;
		this.client = mongoClient;
		this.storageBlocks = storageBlocks;
	}

	@Override
	public List<Protocol> pull(JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add("to", jid.asStringWithBare()).get();
		this.log.debug("Query: " + query);
		Protocols protocols = new Protocols(MongoUtils.findCollection(this.config, this.client).find(query));
		MongoUtils.findCollection(this.config, this.client).remove(query);
		return protocols;
	}

	@Override
	public void push(Protocol protocol) {
		for (StorageBlock blocks : this.storageBlocks) {
			if (blocks.isSupport(protocol)) {
				DBObject entity = BasicDBObjectBuilder.start(blocks.write(protocol)).get();
				this.log.info("Entity: " + entity);
				MongoUtils.findCollection(this.config, this.client).save(entity);
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
