package com.sissi.persistent.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElement;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public class MongoDelayElementBox implements PersistentElementBox, Output {

	private final MongoConfig config;

	private final List<PersistentElement> elements;

	public MongoDelayElementBox(MongoConfig config, List<PersistentElement> elements) {
		super();
		this.config = config;
		this.elements = elements;
	}

	@Override
	public Collection<Element> pull(JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add(PersistentElementBox.fieldTo, jid.asStringWithBare()).add(PersistentElementBox.fieldActivate, true).get();
		Elements elements = new Elements(this.config.collection().find(query));
		this.config.collection().update(query, BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(PersistentElementBox.fieldActivate, false).get()).get(), false, true);
		return elements;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> peek(Map<String, Object> query) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start(query).get()).toMap();
	}

	@Override
	public PersistentElementBox push(Element element) {
		this.doPush(element);
		return this;
	}

	private PersistentElementBox doPush(Element element) {
		for (PersistentElement delay : this.elements) {
			if (delay.isSupport(element)) {
				this.config.collection().update(BasicDBObjectBuilder.start(delay.query(element)).get(), BasicDBObjectBuilder.start(delay.write(element)).get(), true, false);
			}
		}
		return this;
	}

	@Override
	public boolean output(JIDContext context, Element element) {
		this.push(element);
		return true;
	}

	@Override
	public Output close() {
		return this;
	}

	private class Elements extends ArrayList<Element> {

		private final static long serialVersionUID = 1L;

		public Elements(DBCursor cursor) {
			super();
			while (cursor.hasNext()) {
				BasicDBObject each = BasicDBObject.class.cast(cursor.next());
				for (PersistentElement element : MongoDelayElementBox.this.elements) {
					if (element.isSupport(each)) {
						this.add(element.read(each));
					}
				}
			}
		}
	}
}
