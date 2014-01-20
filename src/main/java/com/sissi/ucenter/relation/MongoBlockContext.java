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

	private final List<String> EMPYT_BLOCKS = new ArrayList<String>();

	private final Log log = LogFactory.getLog(this.getClass());

	private final MongoCollection config;

	public MongoBlockContext(MongoCollection config) {
		super();
		this.config = config;
	}

	@Override
	public BlockContext block(JID from, JID to) {
		this.config.collection().update(this.buildQuery(from), this.buildEntity("$addToSet", to));
		return this;
	}

	public BlockContext unblock(JID from) {
		DBObject entity = BasicDBObjectBuilder.start().add("$unset", BasicDBObjectBuilder.start().add(MongoCollection.FIELD_BLOCK, 0).get()).get();
		this.log.debug("Entity: " + entity);
		this.config.collection().update(this.buildQuery(from), entity);
		return this;
	}

	public BlockContext unblock(JID from, JID to) {
		this.config.collection().update(this.buildQuery(from), this.buildEntity("$pull", to));
		return this;
	}

	private DBObject buildQuery(JID from) {
		DBObject query = BasicDBObjectBuilder.start().add("username", from.getUser()).get();
		this.log.debug("Query: " + query);
		return query;
	}

	private DBObject buildEntity(String op, JID to) {
		DBObject entity = BasicDBObjectBuilder.start().add(op, BasicDBObjectBuilder.start().add(MongoCollection.FIELD_BLOCK, to.getUser()).get()).get();
		this.log.debug("Entity: " + entity);
		return entity;
	}

	@Override
	public Boolean isBlock(JID from, JID to) {
		DBObject query = this.buildQuery(from);
		query.put(MongoCollection.FIELD_BLOCK, to.getUser());
		this.log.debug("Query: " + query);
		return this.config.collection().findOne(query, MongoCollection.FILTER_ID) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> iBlockWho(JID from) {
		List<String> bans = (List<String>) this.config.collection().findOne(this.buildQuery(from), MongoCollection.FILTER_BLOCKS).get(MongoCollection.FIELD_BLOCK);
		return bans != null ? bans : EMPYT_BLOCKS;
	}
}
