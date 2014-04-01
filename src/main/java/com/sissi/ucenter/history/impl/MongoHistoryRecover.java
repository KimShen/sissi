package com.sissi.ucenter.history.impl;

import java.util.Collection;
import java.util.List;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.commons.Elements;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.persistent.PersistentElement;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.offline.History.HistoryDirection;
import com.sissi.ucenter.history.HistoryQuery;
import com.sissi.ucenter.history.HistoryRecover;

/**
 * @author kim 2014年3月6日
 */
public class MongoHistoryRecover implements HistoryRecover {

	private final DBObject sort = BasicDBObjectBuilder.start(MongoConfig.FIELD_TIMESTAMP, -1).get();

	private final MongoConfig config;

	private final DefaultParams def;

	private final DefaultParams threshold;

	private final List<PersistentElement> elements;

	public MongoHistoryRecover(MongoConfig config, DefaultParams def, DefaultParams threshold, List<PersistentElement> elements) {
		super();
		this.def = def;
		this.config = config;
		this.elements = elements;
		this.threshold = threshold;
	}

	@Override
	public Collection<Element> pull(JID group, HistoryQuery query) {
		return new Elements(this.config.collection().find(this.build(group, query)).sort(this.sort).limit(query.limit(this.threshold.limit(), this.def.limit())), this.elements);
	}

	private DBObject build(JID group, HistoryQuery query) {
		return BasicDBObjectBuilder.start().add(MongoConfig.FIELD_TO, group.asStringWithBare()).add(MongoConfig.FIELD_CLASS, Message.class.getSimpleName()).add(MongoConfig.FIELD_TYPE, MessageType.GROUPCHAT.toString()).add(MongoConfig.FIELD_TIMESTAMP, BasicDBObjectBuilder.start(query.direction(HistoryDirection.DOWN) ? "$gte" : "$lte", query.since(this.threshold.since(), this.def.since())).get()).get();
	}
}
