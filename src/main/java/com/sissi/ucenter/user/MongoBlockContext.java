package com.sissi.ucenter.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDs;
import com.sissi.ucenter.BlockContext;

/**
 * @author kim 2013年12月6日
 */
public class MongoBlockContext implements BlockContext {

	private final String fieldBlock = "blocks";

	private final DBObject filterId = BasicDBObjectBuilder.start("_id", 1).get();

	private final DBObject filterBlock = BasicDBObjectBuilder.start(fieldBlock, 1).get();

	private final EmptyJIDs empty = new EmptyJIDs();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoBlockContext(MongoConfig config, JIDBuilder jidBuilder) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public BlockContext block(JID from, JID to) {
		this.config.collection().update(this.buildQuery(from), this.buildEntity("$addToSet", to));
		return this;
	}

	public BlockContext unblock(JID from) {
		this.config.collection().update(this.buildQuery(from), BasicDBObjectBuilder.start("$unset", BasicDBObjectBuilder.start(this.fieldBlock, 0).get()).get());
		return this;
	}

	public BlockContext unblock(JID from, JID to) {
		this.config.collection().update(this.buildQuery(from), this.buildEntity("$pull", to));
		return this;
	}

	private DBObject buildQuery(JID from) {
		return BasicDBObjectBuilder.start(MongoConfig.FIELD_USERNAME, from.user()).get();
	}

	private DBObject buildEntity(String op, JID to) {
		return BasicDBObjectBuilder.start(op, BasicDBObjectBuilder.start(this.fieldBlock, to.user()).get()).get();
	}

	@Override
	public boolean isBlock(JID from, JID to) {
		DBObject query = this.buildQuery(from);
		query.put(this.fieldBlock, to.user());
		return this.config.collection().findOne(query, this.filterId) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JIDs iBlockWho(JID from) {
		List<String> bans = (List<String>) this.config.collection().findOne(this.buildQuery(from), this.filterBlock).get(this.fieldBlock);
		return bans != null ? new GroupJIDs(bans) : this.empty;
	}

	private class GroupJIDs extends ArrayList<JID> implements JIDs {

		private static final long serialVersionUID = 1L;

		private GroupJIDs(List<String> bans) {
			for (String ban : bans) {
				super.add(MongoBlockContext.this.jidBuilder.build(ban, null));
			}
		}

		@Override
		public boolean lessThan(Integer counter) {
			return super.size() <= counter;
		}
	}

	private class EmptyJIDs implements JIDs {

		@Override
		public Iterator<JID> iterator() {
			return new Iterator<JID>() {

				@Override
				public boolean hasNext() {
					return false;
				}

				@Override
				public JID next() {
					return null;
				}

				@Override
				public void remove() {
				}
			};
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public boolean lessThan(Integer counter) {
			return false;
		}
	}
}
