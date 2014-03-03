package com.sissi.persistent.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	private final DBObject[] activate = new DBObject[] { BasicDBObjectBuilder.start(PersistentElementBox.fieldActivate, true).get(), BasicDBObjectBuilder.start(PersistentElementBox.fieldAck, true).get() };

	private final MongoConfig config;

	private final String[] support;

	private final List<PersistentElement> elements;

	public MongoDelayElementBox(MongoConfig config, Set<Class<? extends Element>> classes, List<PersistentElement> elements) {
		super();
		this.config = config;
		this.elements = elements;
		List<String> support = new ArrayList<String>();
		for (Class<? extends Element> clazz : classes) {
			support.add(clazz.getSimpleName());
		}
		this.support = support.toArray(new String[] {});
	}

	@Override
	public Collection<Element> pull(JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add(PersistentElementBox.fieldTo, jid.asStringWithBare()).add("$or", this.activate).add(PersistentElementBox.fieldClass, BasicDBObjectBuilder.start("$in", this.support).get()).get();
		Elements elements = new Elements(this.config.collection().find(query));
		this.config.collection().update(query, BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(PersistentElementBox.fieldActivate, false).get()).get(), false, true);
		return elements;
	}

	@Override
	public PersistentElementBox push(Element element) {
		for (PersistentElement delay : this.elements) {
			if (delay.isSupport(element)) {
				this.config.collection().update(BasicDBObjectBuilder.start(delay.query(element)).get(), BasicDBObjectBuilder.start(delay.write(element)).get(), true, false);
			}
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> peek(Map<String, Object> query) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start(query).get()).toMap();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> peek(Map<String, Object> query, Map<String, Object> update) {
		return this.config.collection().findAndModify(BasicDBObjectBuilder.start(query).add("$or", this.support).get(), BasicDBObjectBuilder.start(update).get()).toMap();
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
			try {
				while (cursor.hasNext()) {
					@SuppressWarnings("unchecked")
					Map<String, Object> each = cursor.next().toMap();
					for (PersistentElement element : MongoDelayElementBox.this.elements) {
						if (element.isSupport(each)) {
							this.add(element.read(each));
						}
					}
				}
			} finally {
				cursor.close();
			}
		}
	}
}
