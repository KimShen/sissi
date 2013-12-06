package com.sissi.ucenter.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.impl.MongoCollection;
import com.sissi.context.JID;
import com.sissi.ucenter.BanContext;

/**
 * @author kim 2013年12月6日
 */
public class MongoBanContext implements BanContext {

	private final static String FIELD_ID = "_id";

	private final static String FIELD_BANS = "bans";

	private final static List<String> EMPYT_BANS = new ArrayList<String>();

	private final static DBObject FILTER_DEFAULT = BasicDBObjectBuilder.start(FIELD_ID, 1).get();

	private final static DBObject FILTER_BANS = BasicDBObjectBuilder.start(FIELD_BANS, 1).get();

	private final Log log = LogFactory.getLog(this.getClass());

	private final MongoCollection config;

	public MongoBanContext(MongoCollection config) {
		super();
		this.config = config;
	}

	@Override
	public void ban(JID from, JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).get();
		DBObject entity = BasicDBObjectBuilder.start().add("$addToSet", BasicDBObjectBuilder.start().add("bans", to.getUser()).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		this.config.find().update(query, entity);
	}

	public void free(JID from, JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).get();
		DBObject entity = BasicDBObjectBuilder.start().add("$pull", BasicDBObjectBuilder.start().add("bans", to.getUser()).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		this.config.find().update(query, entity);
	}
	
	public void free(JID from) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).get();
		DBObject entity = BasicDBObjectBuilder.start().add("$unset", BasicDBObjectBuilder.start().add("bans", 0).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		this.config.find().update(query, entity);
	}

	@Override
	public Boolean isBan(JID from, JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).add("bans", to.getUser()).get();
		this.log.debug("Query: " + query);
		return this.config.find().findOne(query, FILTER_DEFAULT) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> iBanedWho(JID from) {
		DBObject query = BasicDBObjectBuilder.start("username", from.getUser()).get();
		this.log.debug("Query is: " + query);
		List<String> bans = (List<String>) this.config.find().findOne(query, FILTER_BANS).get("bans");
		return bans != null ? bans : EMPYT_BANS;
	}
}
