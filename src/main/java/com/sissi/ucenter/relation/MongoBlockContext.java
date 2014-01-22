package com.sissi.ucenter.relation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.BlockContext;

/**
 * @author kim 2013年12月6日
 */
public class MongoBlockContext implements BlockContext {

	private final String block = "blocks";

	private final DBObject filterId = BasicDBObjectBuilder.start("_id", 1).get();

	private final DBObject filterBlock = BasicDBObjectBuilder.start(block, 1).get();

	private final List<String> empty = Collections.unmodifiableList(new ArrayList<String>());

	private final MongoConfig config;

	public MongoBlockContext(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public BlockContext block(JID from, JID to) {
		this.config.collection().update(this.buildQuery(from), this.buildEntity("$addToSet", to));
		return this;
	}

	public BlockContext unblock(JID from) {
		this.config.collection().update(this.buildQuery(from), BasicDBObjectBuilder.start().add("$unset", BasicDBObjectBuilder.start().add(this.block, 0).get()).get());
		return this;
	}

	public BlockContext unblock(JID from, JID to) {
		this.config.collection().update(this.buildQuery(from), this.buildEntity("$pull", to));
		return this;
	}

	private DBObject buildQuery(JID from) {
		return BasicDBObjectBuilder.start().add("username", from.getUser()).get();
	}

	private DBObject buildEntity(String op, JID to) {
		return BasicDBObjectBuilder.start().add(op, BasicDBObjectBuilder.start().add(this.block, to.getUser()).get()).get();
	}

	@Override
	public Boolean isBlock(JID from, JID to) {
		DBObject query = this.buildQuery(from);
		query.put(this.block, to.getUser());
		return this.config.collection().findOne(query, this.filterId) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> iBlockWho(JID from) {
		List<String> bans = (List<String>) this.config.collection().findOne(this.buildQuery(from), this.filterBlock).get(this.block);
		return bans != null ? bans : this.empty;
	}
}
