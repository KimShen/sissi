package com.sissi.persistent.impl;

import java.util.Collection;
import java.util.List;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.persistent.PersistentElement;
import com.sissi.persistent.Recover;
import com.sissi.persistent.RecoverDirection;
import com.sissi.persistent.RecoverQuery;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * 索引策略: {"to":1,"class":1,"type":1,"timestamp":1}
 * 
 * @author kim 2014年3月6日
 */
public class MongoRecover implements Recover {

	/**
	 * {"timestamp":-1}
	 */
	private final DBObject sort = BasicDBObjectBuilder.start(Dictionary.FIELD_TIMESTAMP, -1).get();

	private final MongoConfig config;

	private final DefaultParams def;

	private final DefaultParams threshold;

	private final List<PersistentElement> elements;

	/**
	 * @param config
	 * @param def 默认值
	 * @param threshold 临界值
	 * @param elements
	 */
	public MongoRecover(MongoConfig config, DefaultParams def, DefaultParams threshold, List<PersistentElement> elements) {
		super();
		this.def = def;
		this.config = config;
		this.elements = elements;
		this.threshold = threshold;
	}

	@Override
	public Collection<Element> pull(JID jid, RecoverQuery query) {
		return new Elements(this.config.collection().find(this.build(jid, query)).sort(this.sort).limit(query.limit(this.threshold.limit(), this.def.limit())), this.elements);
	}

	/**
	 * {"to":jid.bare,"class":Xxx,"type":"groupchat","timestamp":{"$gte/$lte":Xxx}}
	 * 
	 * @param jid
	 * @param query
	 * @return
	 */
	private DBObject build(JID jid, RecoverQuery query) {
		return BasicDBObjectBuilder.start().add(Dictionary.FIELD_TO, jid.asStringWithBare()).add(Dictionary.FIELD_CLASS, Message.class.getSimpleName()).add(Dictionary.FIELD_TYPE, MessageType.GROUPCHAT.toString()).add(Dictionary.FIELD_TIMESTAMP, BasicDBObjectBuilder.start(query.direction(RecoverDirection.DOWN) ? "$gte" : "$lte", query.since(this.threshold.since(), this.def.since())).get()).get();
	}
}
