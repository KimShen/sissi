package com.sissi.ucenter.muc.impl;

import java.util.List;
import java.util.Set;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年3月19日
 */
public class MongoRelationMuc4RoleContext extends MongoRelationMucContext {

	private final DBObject aggregateProjectSubscribed = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_RESOURCE, "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_NICK).get()).get();

	private final DBObject aggregateProjectRole = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_ID + "." + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_CREATOR, "$" + MongoConfig.FIELD_ID + "." + MongoConfig.FIELD_CREATOR).add(MongoConfig.FIELD_AFFILIATIONS, "$" + MongoConfig.FIELD_ID + "." + MongoConfig.FIELD_AFFILIATIONS).add(MongoConfig.FIELD_ROLES, "$" + MongoConfig.FIELD_ROLES).get()).get();

	private final DBObject aggregateGroupRole = BasicDBObjectBuilder.start("$group", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ID, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_CREATOR, "$" + MongoConfig.FIELD_CREATOR).add(MongoConfig.FIELD_AFFILIATIONS, "$" + MongoConfig.FIELD_AFFILIATIONS).get()).add(MongoConfig.FIELD_ROLES, BasicDBObjectBuilder.start("$addToSet", "$" + MongoConfig.FIELD_ROLES).get()).get()).get();

	public MongoRelationMuc4RoleContext(boolean activate, String mapping, MongoConfig config, JIDBuilder jidBuilder) throws Exception {
		super(activate, mapping, config, jidBuilder);
	}

	@Override
	// With Resource(Nickname)
	public Set<JID> iSubscribedWho(JID from) {
		DBObject match = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_RESOURCE, from.resource()).get()).get();
		return new JIDGroup(Extracter.asList(this.config.collection().aggregate(match, BasicDBObjectBuilder.start("$unwind", "$" + MongoConfig.FIELD_ROLES).get(), match, this.aggregateProjectSubscribed, super.aggregateLimit).getCommandResult(), MongoConfig.FIELD_RESULT));
	}

	public Set<Relation> myRelations(JID from, String role) {
		AggregationOutput output = super.config.collection().aggregate(this.buildMatcher(from), this.aggregateUnwindRoles, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_ROLE, role).get()).get(), this.aggregateGroupRole, this.aggregateProjectRole);
		List<?> result = Extracter.asList(output.getCommandResult(), MongoConfig.FIELD_RESULT);
		return result.isEmpty() ? this.emptyRelations : new MongoRelations(DBObject.class.cast(result.get(0)));
	}

	@Override
	public MongoRelationMucContext update(JID from, JID to, String status) {
		super.config.collection().update(BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES + "." + super.fieldPath, from.asString()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + ".$." + MongoConfig.FIELD_ROLE, status).get()).get(), true, false, WriteConcern.SAFE);
		return this;
	}
}
