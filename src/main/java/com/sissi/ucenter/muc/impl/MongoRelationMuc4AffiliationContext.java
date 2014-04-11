package com.sissi.ucenter.muc.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucAffiliationBuilder;

/**
 * @author kim 2014年3月19日
 */
public class MongoRelationMuc4AffiliationContext extends MongoRelationMucContext {

	private final String emptyName = "n/a";

	private final DBObject aggregateProject = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_AFFILIATION, "$" + MongoConfig.FIELD_AFFILIATIONS).get()).get();

	private final DBObject aggregateProjectSubscribed = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_RESOURCE, "$" + MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_NICK).get()).get();

	private final DBObject filter = BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, 1).add(MongoConfig.FIELD_CONFIGS + "." + MongoConfig.FIELD_SUBJECT, 1).add(MongoConfig.FIELD_CREATOR, 1).get();

	private final MucAffiliationBuilder mucAffiliationBuilder;

	private final boolean cascade;

	public MongoRelationMuc4AffiliationContext(boolean activate, boolean cascade, String mapping, MongoConfig config, JIDBuilder jidBuilder, MucAffiliationBuilder mucAffiliationBuilder) throws Exception {
		super(activate, mapping, config, jidBuilder);
		this.mucAffiliationBuilder = mucAffiliationBuilder;
		this.cascade = cascade;
	}

	public Set<JID> iSubscribedWho(JID from) {
		DBObject match = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_NICK, BasicDBObjectBuilder.start(MongoConfig.FIELD_NICK, BasicDBObjectBuilder.start("$exists", true).get()).get()).get()).get();
		return new JIDGroup(Extracter.asList(this.config.collection().aggregate(match, this.aggregateUnwindAffiliation, match, this.aggregateProjectSubscribed).getCommandResult(), MongoConfig.FIELD_RESULT));
	}

	public Set<Relation> myRelations(JID from) {
		return new Relations(this.config.collection().find(BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).get(), this.filter));
	}

	public Set<Relation> myRelations(JID from, String affiliation) {
		AggregationOutput output = super.config.collection().aggregate(super.buildMatcher(from), super.aggregateUnwindAffiliation, BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_AFFILIATION, affiliation).get()).get(), this.aggregateProject);
		List<?> result = Extracter.asList(output.getCommandResult(), MongoConfig.FIELD_RESULT);
		return result.isEmpty() ? super.emptyRelations : new AffiliationRelations(result);
	}

	public MongoRelationMucContext remove(JID from, JID to) {
		super.config.collection().update(super.buildQuery(to.asStringWithBare()), BasicDBObjectBuilder.start("$pull", BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATIONS, BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, from.asStringWithBare()).get()).get()).get());
		return this;
	}

	@Override
	public MongoRelationMucContext update(JID from, JID to, String status) {
		this.mucAffiliationBuilder.build(to).approve(from, status);
		if (this.cascade && (ItemAffiliation.OUTCAST.equals(status) || !ItemAffiliation.parse(status).contains(super.mucConfigBuilder.build(to).pull(MongoConfig.FIELD_AFFILIATION, String.class)))) {
			super.remove(from, to, true);
		}
		return this;
	}

	private final class AfflilationRelation implements Relation {

		private final DBObject db;

		private AfflilationRelation(DBObject db) {
			super();
			this.db = db;
		}

		@Override
		public String jid() {
			return Extracter.asString(this.db, MongoConfig.FIELD_JID);
		}

		@Override
		public String name() {
			return Extracter.asString(Extracter.asDBObject(this.db, MongoConfig.FIELD_CONFIGS), MongoConfig.FIELD_SUBJECT, MongoRelationMuc4AffiliationContext.this.emptyName);
		}

		@Override
		public boolean activate() {
			return true;
		}

		@Override
		public Map<String, Object> plus() {
			return MongoRelationMuc4AffiliationContext.super.fieldPlus;
		}

		@Override
		public <T extends Relation> T cast(Class<T> clazz) {
			return clazz.cast(this);
		}

	}

	private final class Relations extends HashSet<Relation> {

		private static final long serialVersionUID = 1L;

		public Relations(DBCursor cursor) {
			try {
				while (cursor.hasNext()) {
					super.add(new AfflilationRelation(cursor.next()));
				}
			} finally {
				cursor.close();
			}
		}
	}

	private final class AffiliationRelations extends HashSet<Relation> {

		private static final long serialVersionUID = 1L;

		private AffiliationRelations(List<?> affiliations) {
			for (Object each : affiliations) {
				DBObject affiliation = Extracter.asDBObject(DBObject.class.cast(each), MongoConfig.FIELD_AFFILIATION);
				super.add(new MucNoneRelation(MongoRelationMuc4AffiliationContext.this.jidBuilder.build(Extracter.asString(affiliation, MongoConfig.FIELD_JID)), ItemAffiliation.parse(Extracter.asString(affiliation, MongoConfig.FIELD_AFFILIATION))));
			}
		}
	}
}
