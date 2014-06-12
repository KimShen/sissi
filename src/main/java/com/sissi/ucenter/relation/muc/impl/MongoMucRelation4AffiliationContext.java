package com.sissi.ucenter.relation.muc.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.affiliation.AffiliationBuilder;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 索引策略1: {"affiliations.jid":1,"affiliations.nick":1}</p>索引策略2: {"affiliations.jid":1}</p>索引策略3: {"jid":1}</p>索引策略4: {"affiliations.affiliation":1}</p>索引策略5: {"configs.persistent":1}
 * 
 * @author kim 2014年4月25日
 */
public class MongoMucRelation4AffiliationContext extends MongoMucRelationContext {

	/**
	 * 默认Relation.name
	 */
	private final String name = "n/a";

	/**
	 * {"$match":"configs.persistent":true}
	 */
	private final DBObject matchPersistent = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_CONFIGS + "." + Dictionary.FIELD_PERSISTENT, true).get()).get();

	/**
	 * {"$project":{"affiliation":"$affiliations"}}
	 */
	private final DBObject projectAffiliation = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(Dictionary.FIELD_AFFILIATION, "$" + Dictionary.FIELD_AFFILIATIONS).get()).get();

	/**
	 * {"$project":{"jid":"$jid","resource":"$affiliations.nick"}}
	 */
	private final DBObject projectSubscribed = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, "$" + Dictionary.FIELD_JID).add(Dictionary.FIELD_RESOURCE, "$" + Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_NICK).get()).get();

	/**
	 * {"configs":1}
	 */
	private final DBObject filterUpdate = BasicDBObjectBuilder.start().add(Dictionary.FIELD_CONFIGS, 1).get();

	/**
	 * {"jid":1,"configs.subject":1,"creator":1}
	 */
	private final DBObject filterRelations = BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, 1).add(Dictionary.FIELD_CONFIGS + "." + Dictionary.FIELD_SUBJECT, 1).add(Dictionary.FIELD_CREATOR, 1).get();

	private final AffiliationBuilder affiliationBuilder;

	private final boolean cascade;

	/**
	 * @param activate
	 * @param cascade 是否Update时级联删除
	 * @param mapping
	 * @param finder
	 * @param config
	 * @param jidBuilder
	 * @param vcardContext
	 * @param affiliationBuilder
	 * @throws Exception
	 */
	public MongoMucRelation4AffiliationContext(boolean cascade, String mapping, MongoConfig config, JIDBuilder jidBuilder, VCardContext vcardContext, AffiliationBuilder affiliationBuilder) throws Exception {
		super(mapping, config, jidBuilder, vcardContext);
		this.affiliationBuilder = affiliationBuilder;
		this.cascade = cascade;
	}

	/*
	 * 我订阅的所有房间(存在岗位)
	 * 
	 * @see com.sissi.ucenter.relation.RelationContext#iSubscribedWho(com.sissi.context.JID)
	 */
	public Set<JID> iSubscribedWho(JID from) {
		// {"$match":{"affiliations.jid",from.bare,"affiliations.nick":{"$exists":true}}}
		DBObject match = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_NICK, BasicDBObjectBuilder.start("$exists", true).get()).get()).get();
		return new JIDGroup(MongoUtils.asList(this.config.collection().aggregate(this.matchPersistent, match, super.unwindAffiliation, match, this.projectSubscribed).getCommandResult(), Dictionary.FIELD_RESULT));
	}

	/*
	 * JID已加入所有房间(存在岗位)
	 * 
	 * @see com.sissi.ucenter.relation.muc.impl.MongoRelationMucContext#myRelations(com.sissi.context.JID)
	 */
	public Set<Relation> myRelations(JID from) {
		// {"affiliations.jid":from.bare}, {"jid":1,"configs.subject":1,"creator":1}, 只需要JID和房间名称
		return new Relations(this.config.collection().find(BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_JID, from.asStringWithBare()).get(), this.filterRelations));
	}

	/*
	 * 岗位相符的订阅关系
	 * 
	 * @see com.sissi.ucenter.relation.muc.MucRelationContext#myRelations(com.sissi.context.JID, java.lang.String)
	 */
	public Set<Relation> myRelations(JID from, String affiliation) {
		// {"$match":{"jid":group.bare}}, {"$unwind":"$affiliations"}, {"$match":{"affiliations.affiliation":Xxx}}, {"$project":{"affiliation":"$affiliations"}}
		AggregationOutput output = super.config.collection().aggregate(super.buildMatcher(from), super.unwindAffiliation, BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_AFFILIATION, affiliation).get()).get(), this.projectAffiliation);
		List<?> result = MongoUtils.asList(output.getCommandResult(), Dictionary.FIELD_RESULT);
		return result.isEmpty() ? super.relations : new AffiliationRelations(result);
	}

	/*
	 * 删除订阅关系
	 * 
	 * @see com.sissi.ucenter.relation.muc.impl.MongoRelationMucContext#remove(com.sissi.context.JID, com.sissi.context.JID)
	 */
	public MongoMucRelationContext remove(JID from, JID to) {
		// {"jid":jid}, {"$pull":{"affiliations.jid":Xxx}}
		super.config.collection().update(super.buildQuery(to.asStringWithBare()), BasicDBObjectBuilder.start("$pull", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATIONS, BasicDBObjectBuilder.start(Dictionary.FIELD_JID, from.asStringWithBare()).get()).get()).get());
		return this;
	}

	@Override
	public MongoMucRelationContext update(JID from, JID to, String status) {
		// 1,更新成功 2,允许级联 3,岗位为Outcast或岗位受限制
		if (this.affiliationBuilder.build(to).update(from, status) && this.cascade && (ItemAffiliation.OUTCAST.equals(status) || !ItemAffiliation.parse(status).contains(MongoUtils.asString(MongoUtils.asDBObject(this.config.collection().findOne(BasicDBObjectBuilder.start(Dictionary.FIELD_JID, to.asStringWithBare()).get(), this.filterUpdate), Dictionary.FIELD_CONFIGS), Dictionary.FIELD_AFFILIATION)))) {
			super.remove(from, to, true);
		}
		return this;
	}

	/**
	 * 房间-用户反向订阅关系
	 * 
	 * @author kim 2014年4月25日
	 */
	private final class RoomRelation implements Relation {

		private final DBObject db;

		private RoomRelation(DBObject db) {
			super();
			this.db = db;
		}

		@Override
		public String jid() {
			return MongoUtils.asString(this.db, Dictionary.FIELD_JID);
		}

		/*
		 * 使用Config.subject作为名词,如果不存在则使用默认
		 * 
		 * @see com.sissi.ucenter.relation.Relation#name()
		 */
		@Override
		public String name() {
			return MongoUtils.asString(MongoUtils.asDBObject(this.db, Dictionary.FIELD_CONFIGS), Dictionary.FIELD_SUBJECT, MongoMucRelation4AffiliationContext.this.name);
		}

		@Override
		public boolean activate() {
			return true;
		}

		@Override
		public Map<String, Object> plus() {
			return MongoMucRelation4AffiliationContext.super.plus;
		}

		@Override
		public <T extends Relation> T cast(Class<T> clazz) {
			return clazz.cast(this);
		}

	}

	private final class Relations extends HashSet<Relation> {

		private static final long serialVersionUID = 1L;

		public Relations(DBCursor cursor) {
			try (DBCursor iterator = cursor) {
				while (iterator.hasNext()) {
					super.add(new RoomRelation(iterator.next()));
				}
			}
		}
	}

	/**
	 * 岗位相符的订阅关系
	 * 
	 * @author kim 2014年4月25日
	 */
	private final class AffiliationRelations extends HashSet<Relation> {

		private static final long serialVersionUID = 1L;

		private AffiliationRelations(List<?> affiliations) {
			for (Object each : affiliations) {
				DBObject affiliation = MongoUtils.asDBObject(DBObject.class.cast(each), Dictionary.FIELD_AFFILIATION);
				super.add(new NoneRelation(MongoMucRelation4AffiliationContext.this.jidBuilder.build(MongoUtils.asString(affiliation, Dictionary.FIELD_JID)), ItemAffiliation.parse(MongoUtils.asString(affiliation, Dictionary.FIELD_AFFILIATION))));
			}
		}
	}
}
