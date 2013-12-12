package com.sissi.ucenter.relation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.impl.MongoCollection;
import com.sissi.context.JID;
import com.sissi.ucenter.BlockContext;

/**
 * @author kim 2013年12月6日
 */
public class MongoBlockContext implements BlockContext {

	private final static String FIELD_BLOCK = "blocks";

	private final static List<String> EMPYT_BLOCKS = new ArrayList<String>();

	private final static DBObject FILTER_DEFAULT = BasicDBObjectBuilder.start("_id", 1).get();

	private final static DBObject FILTER_BLOCKS = BasicDBObjectBuilder.start(FIELD_BLOCK, 1).get();

	private final Log log = LogFactory.getLog(this.getClass());

	private final MongoCollection config;

	public MongoBlockContext(MongoCollection config) {
		super();
		this.config = config;
	}

	@Override
	public BlockContext block(JID from, JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).get();
		DBObject entity = BasicDBObjectBuilder.start().add("$addToSet", BasicDBObjectBuilder.start().add(FIELD_BLOCK, to.getUser()).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		this.config.collection().update(query, entity);
		return this;
	}

	public BlockContext unblock(JID from, JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).get();
		DBObject entity = BasicDBObjectBuilder.start().add("$pull", BasicDBObjectBuilder.start().add(FIELD_BLOCK, to.getUser()).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		this.config.collection().update(query, entity);
		return this;
	}

	public BlockContext unblock(JID from) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).get();
		DBObject entity = BasicDBObjectBuilder.start().add("$unset", BasicDBObjectBuilder.start().add(FIELD_BLOCK, 0).get()).get();
		this.log.debug("Query: " + query);
		this.log.debug("Entity: " + entity);
		this.config.collection().update(query, entity);
		return this;
	}

	@Override
	public Boolean isBlock(JID from, JID to) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).add(FIELD_BLOCK, to.getUser()).get();
		this.log.debug("Query: " + query);
		return this.config.collection().findOne(query, FILTER_DEFAULT) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> iBlockWho(JID from) {
		DBObject query = BasicDBObjectBuilder.start("username", from.getUser()).get();
		this.log.debug("Query is: " + query);
		List<String> bans = (List<String>) this.config.collection().findOne(query, FILTER_BLOCKS).get(FIELD_BLOCK);
		return bans != null ? bans : EMPYT_BLOCKS;
	}
}
