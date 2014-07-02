package com.sissi.persistent.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.persistent.Persistent;
import com.sissi.persistent.PersistentElement;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Element;

/**
 * 索引策略1: {"activate":1,"ack":1}</p>索引策略2: {"to":1,"activate":1,"ack":1,"class":1,"resend":1}</p>索引策略3: {"sid":1,"calss":1}</p>索引策略4: {"pid":1,"calss":1}</p>索引策略5: {"pid":1}
 * 
 * @author kim 2013-11-15
 */
public class MongoPersistent implements Persistent, Output {

	/**
	 * {"activate":true,"ack":false}
	 */
	private final DBObject[] activate = new DBObject[] { BasicDBObjectBuilder.start(Dictionary.FIELD_ACTIVATE, true).get(), BasicDBObjectBuilder.start(Dictionary.FIELD_ACK, false).get() };

	/**
	 * 支持的XMPP节类型</p>{"in":[Xxx,Xxx]}
	 */
	private final DBObject support;

	private final int resend;

	private final MongoConfig config;

	private final List<PersistentElement> elements;

	/**
	 * @param resend 最大重发次数
	 * @param config
	 * @param elements
	 */
	public MongoPersistent(int resend, MongoConfig config, List<PersistentElement> elements) {
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

	public boolean exists(String pid) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start(Dictionary.FIELD_PID, pid).get()) != null;
	}

	/*
	 * Upsert that</p>Query:{"to",jid.bare,"$or":[@see activate],"class":支持的XMPP节类型,"resend",{"$lt":Xxx}}</p>Entity:{"$set":{"activate":false,"$inc":{"resend":1}}
	 * 
	 * @see com.sissi.persistent.PersistentElementBox#pull(com.sissi.context.JID)
	 */
	@Override
	public Collection<Element> pull(JID jid) {
		DBObject query = BasicDBObjectBuilder.start().add(Dictionary.FIELD_TO, jid.asStringWithBare()).add("$or", this.activate).add(Dictionary.FIELD_CLASS, this.support).add(Dictionary.FIELD_RESEND, BasicDBObjectBuilder.start("$lt", this.resend).get()).get();
		Elements elements = new Elements(this.config.collection().find(query), this.elements);
		// {"$set:{"activate":false,"$inc":{"resend":1}}}
		this.config.collection().update(query, BasicDBObjectBuilder.start().add("$set", BasicDBObjectBuilder.start(Dictionary.FIELD_ACTIVATE, false).get()).add("$inc", BasicDBObjectBuilder.start(Dictionary.FIELD_RESEND, 1).get()).get(), false, true);
		return elements;
	}

	/*
	 * Upsert that</p>Query: PersistentElement.query</p>Entity: PersistentElement.write
	 * 
	 * @see com.sissi.persistent.PersistentElementBox#push(com.sissi.protocol.Element)
	 */
	@Override
	public Persistent push(Element element) {
		for (PersistentElement each : this.elements) {
			if (each.isSupport(element)) {
				this.config.collection().update(BasicDBObjectBuilder.start(each.query(element)).get(), BasicDBObjectBuilder.start(each.write(element)).get(), true, false);
			}
		}
		return this;
	}

	public Map<String, Object> peek(Map<String, Object> query) {
		return MongoUtils.asMap(this.config.collection().findOne(BasicDBObjectBuilder.start(query).get()));
	}

	/*
	 * FindAndModify
	 * 
	 * @see com.sissi.persistent.PersistentElementBox#peek(java.util.Map, java.util.Map)
	 */
	public Map<String, Object> peek(Map<String, Object> query, Map<String, Object> update) {
		return MongoUtils.asMap(this.config.collection().findAndModify(BasicDBObjectBuilder.start(query).add(Dictionary.FIELD_CLASS, this.support).get(), BasicDBObjectBuilder.start(update).get()));
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
