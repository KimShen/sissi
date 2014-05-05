package com.sissi.ucenter.block.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDs;
import com.sissi.context.impl.OfflineJID;
import com.sissi.ucenter.block.BlockContext;

/**
 * 索引策略1:{"username":1}</p>索引策略2:{"username":Xxx,"blocks":Xxx}
 * 
 * @author kim 2013年12月6日
 */
public class MongoBlockContext implements BlockContext {

	private final String blocks = "blocks";

	private final DBObject filterId = BasicDBObjectBuilder.start("_id", 1).get();

	private final DBObject filterBlock = BasicDBObjectBuilder.start(this.blocks, 1).get();

	private final EmptyJIDs empty = new EmptyJIDs();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoBlockContext(MongoConfig config, JIDBuilder jidBuilder) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
	}

	/*
	 * {"username":Xxx},{"$addToSet":{"blocks":Xxx}}
	 * 
	 * @see com.sissi.ucenter.user.block.BlockContext#block(com.sissi.context.JID, com.sissi.context.JID)
	 */
	@Override
	public BlockContext block(JID from, JID to) {
		this.config.collection().update(this.buildQuery(from), this.buildEntity("$addToSet", to));
		return this;
	}

	/*
	 * {"username":Xxx},{"$unset":{"blocks":0}}
	 * 
	 * @see com.sissi.ucenter.user.block.BlockContext#unblock(com.sissi.context.JID)
	 */
	public BlockContext unblock(JID from) {
		this.config.collection().update(this.buildQuery(from), BasicDBObjectBuilder.start("$unset", BasicDBObjectBuilder.start(this.blocks, 0).get()).get());
		return this;
	}

	/*
	 * {"username":Xxx},{"$pullaZ":{"blocks":Xxx}}
	 * 
	 * @see com.sissi.ucenter.user.block.BlockContext#unblock(com.sissi.context.JID, com.sissi.context.JID)
	 */
	public BlockContext unblock(JID from, JID to) {
		this.config.collection().update(this.buildQuery(from), this.buildEntity("$pull", to));
		return this;
	}

	/**
	 * {"username":Xxx}
	 * 
	 * @param from
	 * @return
	 */
	private DBObject buildQuery(JID from) {
		return BasicDBObjectBuilder.start(Dictionary.FIELD_USERNAME, from.user()).get();
	}

	/**
	 * {op:{"blocks":Xxx}}
	 * 
	 * @param op
	 * @param to
	 * @return
	 */
	private DBObject buildEntity(String op, JID to) {
		return BasicDBObjectBuilder.start(op, BasicDBObjectBuilder.start(this.blocks, to.user()).get()).get();
	}

	/*
	 * {"username":Xxx,"blocks":Xxx}
	 * 
	 * @see com.sissi.ucenter.user.block.BlockContext#isBlock(com.sissi.context.JID, com.sissi.context.JID)
	 */
	@Override
	public boolean isBlock(JID from, JID to) {
		DBObject query = this.buildQuery(from);
		query.put(this.blocks, to.user());
		return this.config.collection().findOne(query, this.filterId) != null;
	}

	/*
	 * {"username":Xxx},{"blocks":1}
	 * 
	 * @see com.sissi.ucenter.user.block.BlockContext#iBlockWho(com.sissi.context.JID)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JIDs iBlockWho(JID from) {
		List<String> bans = (List<String>) this.config.collection().findOne(this.buildQuery(from), this.filterBlock).get(this.blocks);
		return bans != null ? new GroupJIDs(bans) : this.empty;
	}

	private class GroupJIDs extends ArrayList<JID> implements JIDs {

		private final static long serialVersionUID = 1L;

		private GroupJIDs(List<String> jids) {
			for (String jid : jids) {
				super.add(MongoBlockContext.this.jidBuilder.build(jid, null));
			}
		}

		@Override
		public boolean lessThan(Integer counter) {
			return super.size() <= counter;
		}

		@Override
		public JID jid() {
			return OfflineJID.OFFLINE;
		}

		@Override
		public boolean same(JID jid) {
			return false;
		}

		@Override
		public boolean like(JID jid) {
			return false;
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
					return OfflineJID.OFFLINE;
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

		@Override
		public JID jid() {
			return OfflineJID.OFFLINE;
		}

		@Override
		public boolean same(JID jid) {
			return false;
		}

		@Override
		public boolean like(JID jid) {
			return false;
		}
	}
}
