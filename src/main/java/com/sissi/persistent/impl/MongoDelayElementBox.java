package com.sissi.persistent.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.commons.Elements;
import com.sissi.commons.Extracter;
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

	private final DBObject[] activate = new DBObject[] { BasicDBObjectBuilder.start(MongoConfig.FIELD_ACTIVATE, true).get(), BasicDBObjectBuilder.start(PersistentElementBox.fieldAck, false).get() };

	private final DBObject support;

	private final int resend;

	private final MongoConfig config;

	private final List<PersistentElement> elements;

	public MongoDelayElementBox(int resend, MongoConfig config, List<PersistentElement> elements) {
		super();
		this.resend = resend;
		this.config = config;
		this.elements = elements;
		List<String> support = new ArrayList<String>();
		for (PersistentElement each : elements) {
			support.add(each.support().getSimpleName());
		}
		this.support = BasicDBObjectBuilder.start("$in", support.toArray(new String[] {})).get();
	}

	@Override
	public Collection<Element> pull(JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add(MongoConfig.FIELD_TO, jid.asStringWithBare()).add("$or", this.activate).add(MongoConfig.FIELD_CLASS, this.support).add(PersistentElementBox.fieldResend, BasicDBObjectBuilder.start("$lt", this.resend).get()).get();
		Elements elements = new Elements(this.config.collection().find(query), this.elements);
		this.config.collection().update(query, BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(MongoConfig.FIELD_ACTIVATE, false).get()).add("$inc", BasicDBObjectBuilder.start(PersistentElementBox.fieldResend, 1).get()).get(), false, true);
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

	public Map<String, Object> peek(Map<String, Object> query) {
		return Extracter.asMap(this.config.collection().findOne(BasicDBObjectBuilder.start(query).get()));
	}

	public Map<String, Object> peek(Map<String, Object> query, Map<String, Object> update) {
		return Extracter.asMap(this.config.collection().findAndModify(BasicDBObjectBuilder.start(query).add(MongoConfig.FIELD_CLASS, this.support).get(), BasicDBObjectBuilder.start(update).get()));
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
}
