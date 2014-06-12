package com.sissi.ucenter.relation.muc.impl;

import java.util.List;
import java.util.Set;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 索引策略1: {"roles.jid":1,"roles.resource":1}</p>索引策略2: {"jid":1}}</p>索引策略3: {"roles.role":1}</p>索引策略4: {"roles.path":1}
 * 
 * @author kim 2014年3月19日
 */
public class MongoMucRelation4RoleContext extends MongoMucRelationContext {

	/**
	 * {"$project":{"jid":"$jid","resource":"$roles.nick"}}
	 */
	private final DBObject projectSubscribed = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, "$" + Dictionary.FIELD_JID).add(Dictionary.FIELD_RESOURCE, "$" + Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_NICK).get()).get();

	/**
	 * {"$project":{"jid":"$_id.jid","creator":"$_id.creator","affiliations":"$_id.affiliations","roles":"$roles"}}
	 */
	private final DBObject projectRole = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, "$" + Dictionary.FIELD_ID + "." + Dictionary.FIELD_JID).add(Dictionary.FIELD_CREATOR, "$" + Dictionary.FIELD_ID + "." + Dictionary.FIELD_CREATOR).add(Dictionary.FIELD_AFFILIATIONS, "$" + Dictionary.FIELD_ID + "." + Dictionary.FIELD_AFFILIATIONS).add(Dictionary.FIELD_ROLES, "$" + Dictionary.FIELD_ROLES).get()).get();

	/**
	 * {"$group":{"_id":{"jid":"$jid","creator":"$creator","affiliations":"$affiliations"},"roles":{"$addToSet":"$roles"}}}
	 */
	private final DBObject group = BasicDBObjectBuilder.start("$group", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ID, BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, "$" + Dictionary.FIELD_JID).add(Dictionary.FIELD_CREATOR, "$" + Dictionary.FIELD_CREATOR).add(Dictionary.FIELD_AFFILIATIONS, "$" + Dictionary.FIELD_AFFILIATIONS).get()).add(Dictionary.FIELD_ROLES, BasicDBObjectBuilder.start("$addToSet", "$" + Dictionary.FIELD_ROLES).get()).get()).get();

	public MongoMucRelation4RoleContext(String mapping, MongoConfig config, JIDBuilder jidBuilder, VCardContext vcardContext) throws Exception {
		super(mapping, config, jidBuilder, vcardContext);
	}

	/*
	 * 我在订阅房间中的MUC JID(含Resource)
	 * 
	 * @see com.sissi.ucenter.relation.RelationContext#iSubscribedWho(com.sissi.context.JID)
	 */
	@Override
	public Set<JID> iSubscribedWho(JID from) {
		// {"$match":{"roles.jid":from.bare,"roles.resource":from.resource}}
		DBObject match = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_JID, from.asStringWithBare()).add(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_RESOURCE, from.resource()).get()).get();
		// match, {"$unwind":"$roles"}, match, {"$project":{"jid":"$jid","resource":"$roles.nick"}}
		return new JIDGroup(MongoUtils.asList(this.config.collection().aggregate(match, super.unwindRoles, match, this.projectSubscribed).getCommandResult(), Dictionary.FIELD_RESULT));
	}

	/*
	 * 角色相符的订阅关系
	 * 
	 * @see com.sissi.ucenter.relation.muc.MucRelationContext#myRelations(com.sissi.context.JID, java.lang.String)
	 */
	public Set<Relation> myRelations(JID from, String role) {
		// {"$match":{"jid":group.bare}}, {"$unwind":"$roles"}, {"$match":{"roles.role":Xxx}}, {"$group":{"_id":{"jid":"$jid","creator":"$creator","affiliations":"$affiliations"},"roles":{"$addToSet":"$roles"}}}, {"$project":{"jid":"$_id.jid","creator":"$_id.creator","affiliations":"$_id.affiliations","roles":"$roles"}}
		AggregationOutput output = super.config.collection().aggregate(this.buildMatcher(from), super.unwindRoles, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_ROLE, role).get()).get(), this.group, this.projectRole);
		List<?> result = MongoUtils.asList(output.getCommandResult(), Dictionary.FIELD_RESULT);
		return result.isEmpty() ? this.relations : new MongoRelations(DBObject.class.cast(result.get(0)));
	}

	@Override
	public MongoMucRelationContext update(JID from, JID to, String status) {
		// {"roles.path":from}, {"$set":{"roles.role":Xxx}}
		super.config.collection().update(BasicDBObjectBuilder.start(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_PATH, from.asString()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ROLES + ".$." + Dictionary.FIELD_ROLE, status).get()).get(), true, false, WriteConcern.SAFE);
		return this;
	}
}
