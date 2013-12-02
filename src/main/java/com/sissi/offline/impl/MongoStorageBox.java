package com.sissi.offline.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.offline.Storage;
import com.sissi.offline.StorageBox;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public class MongoStorageBox implements StorageBox {

	private Log log = LogFactory.getLog(this.getClass());

	private MongoConfig config;

	private List<Storage> storageBlocks;

	public MongoStorageBox(MongoConfig config, List<Storage> storageBlocks) {
		super();
		this.config = config;
		this.storageBlocks = storageBlocks;
	}

	@Override
	public List<Element> fetch(JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add("to", jid.asStringWithBare()).get();
		this.log.debug("Query: " + query);
		Elements elements = new Elements(this.config.findCollection().find(query));
		this.config.findCollection().remove(query);
		return elements;
	}

	@Override
	public void store(Element element) {
		for (Storage blocks : this.storageBlocks) {
			if (blocks.isSupport(element)) {
				DBObject entity = BasicDBObjectBuilder.start(blocks.write(element)).get();
				this.log.info("Entity: " + entity);
				this.config.findCollection().save(entity);
			}
		}
	}

	private class Elements extends ArrayList<Element> {

		private static final long serialVersionUID = 1L;

		public Elements(DBCursor cursor) {
			super();
			while (cursor.hasNext()) {
				BasicDBObject each = (BasicDBObject) cursor.next();
				for (Storage blocks : MongoStorageBox.this.storageBlocks) {
					if (blocks.isSupport(each)) {
						this.add(blocks.read(each));
					}
				}
			}
		}
	}
}
