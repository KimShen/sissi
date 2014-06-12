package com.sissi.ucenter.relation.roster.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.impl.DefaultRelation;

/**
 * 获取订阅关系. 索引策略1: {"master":1,"slave":1}</p> 索引策略2: {"slave":1,"master":1},
 * 
 * @author kim 2014年4月22日
 */
public class MongoOurRelation {

	private final Map<String, Object> plus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final MongoConfig config;

	private final String[] groups;

	/**
	 * @param config
	 * @param groups 默认Group
	 */
	public MongoOurRelation(MongoConfig config, String[] groups) {
		super();
		this.config = config;
		this.groups = groups;
	}

	public Relation ourRelation(JID from, JID to) {
		DBObject db = this.config.collection().findOne(this.buildQuery(from.asStringWithBare(), to.asStringWithBare()));
		return db != null ? new MongoRosterRelation(db, Dictionary.FIELD_SLAVE, this.groups, this.plus) : new DefaultRelation(to, RosterSubscription.NONE.toString());
	}

	/**
	 * {"master":1,"slave":1}
	 * 
	 * @param master
	 * @param slave
	 * @return
	 */
	public DBObject buildQuery(String master, String slave) {
		return BasicDBObjectBuilder.start().add(Dictionary.FIELD_MASTER, master).add(Dictionary.FIELD_SLAVE, slave).get();
	}
}
