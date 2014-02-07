package com.sissi.offline.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.offline.DelayElement;
import com.sissi.offline.DelayElementBox;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public class MongoDelayElementBox implements DelayElementBox, Output {

	private final String to = "to";

	private final MongoConfig config;

	private final List<DelayElement> elements;

	public MongoDelayElementBox(MongoConfig config, List<DelayElement> elements) {
		super();
		this.config = config;
		this.elements = elements;
	}

	@Override
	public Collection<Element> pull(JID jid) {
		DBObject query = BasicDBObjectBuilder.start(this.to, jid.asStringWithBare()).get();
		Elements elements = new Elements(this.config.collection().find(query));
		this.config.collection().remove(query);
		return elements;
	}

	@Override
	public DelayElementBox push(Element element) {
		this.doPush(element);
		return this;
	}

	private DelayElementBox doPush(Element element) {
		for (DelayElement delay : this.elements) {
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
				for (DelayElement element : MongoDelayElementBox.this.elements) {
					if (element.isSupport(each)) {
						this.add(element.read(each));
					}
				}
			}
		}
	}
}
