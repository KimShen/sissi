package com.sissi.ucenter.relation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoProxyConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.impl.OfflineJID;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.RelationMuc;
import com.sissi.ucenter.RelationMucMapping;

/**
 * @author kim 2014年2月11日
 */
public class MongoRelationMucContext implements RelationContext, RelationMucMapping {

	private final String fieldRole = "role";

	private final String fieldRoles = this.fieldRole + "s";

	private final String fieldResult = "result";

	private final String fieldCreator = "creator";

	private final String fieldActivate = "activate";

	private final String fieldAffiliation = "affiliation";

	private final String fieldAffiliations = this.fieldAffiliation + "s";

	private final Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final DBObject aggregateLimit = BasicDBObjectBuilder.start().add("$limit", 1).get();

	private final DBObject aggregateUnwindRoles = BasicDBObjectBuilder.start().add("$unwind", "$" + this.fieldRoles).get();

	private final DBObject aggregateUnwindAffiliation = BasicDBObjectBuilder.start().add("$unwind", "$" + this.fieldAffiliations).get();

	private final DBObject aggregateSort = BasicDBObjectBuilder.start().add("$sort", BasicDBObjectBuilder.start(this.fieldAffiliation, -1).get()).get();

	private final DBObject aggregateProject = BasicDBObjectBuilder.start().add("$project", BasicDBObjectBuilder.start().add(this.fieldActivate, "$" + this.fieldActivate).add(this.fieldCreator, "$" + this.fieldCreator).add(this.fieldRoles, "$" + this.fieldRoles).add(this.fieldAffiliation, BasicDBObjectBuilder.start().add("$cond", new Object[] { BasicDBObjectBuilder.start("$eq", new String[] { "$" + this.fieldAffiliations + "." + MongoProxyConfig.FIELD_JID, "$" + this.fieldRoles + "." + MongoProxyConfig.FIELD_JID }).get(), "$" + this.fieldAffiliations + "." + this.fieldAffiliation, null }).get()).get()).get();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoRelationMucContext(MongoConfig config, JIDBuilder jidBuilder) throws Exception {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
	}

	private DBObject buildQuery(String jid) {
		return BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_JID, jid).get();
	}

	private JID build(DBObject db) {
		DBObject roles = DBObject.class.cast(db.get(this.fieldRoles));
		return this.jidBuilder.build(this.config.asString(roles, MongoProxyConfig.FIELD_JID)).resource(this.config.asString(roles, MongoProxyConfig.FIELD_RESOURCE));
	}

	@Override
	public MongoRelationMucContext establish(JID from, Relation relation) {
		RelationMuc muc = RelationMuc.class.cast(relation);
		try {
			DBObject query = this.buildQuery(muc.getJID());
			query.put(this.fieldRoles + "." + MongoProxyConfig.FIELD_JID, from.asStringWithBare());
			query.put(this.fieldRoles + "." + MongoProxyConfig.FIELD_RESOURCE, from.resource());
			this.config.collection().update(query, BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(this.fieldRoles + ".$." + this.fieldRole, muc.getRole()).add(this.fieldRoles + ".$." + MongoProxyConfig.FIELD_NICK, muc.getName()).get()).get(), true, false, WriteConcern.SAFE);
		} catch (MongoException e) {
			this.config.collection().update(this.buildQuery(muc.getJID()), BasicDBObjectBuilder.start().add("$setOnInsert", BasicDBObjectBuilder.start().add(this.fieldActivate, false).add(this.fieldCreator, from.asStringWithBare()).add(this.fieldAffiliations, new DBObject[] { BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_JID, from.asStringWithBare()).add(this.fieldAffiliation, ItemAffiliation.OWNER.toString()).get() }).get()).add("$addToSet", BasicDBObjectBuilder.start().add(this.fieldRoles, BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_JID, from.asStringWithBare()).add(MongoProxyConfig.FIELD_RESOURCE, from.resource()).add(MongoProxyConfig.FIELD_NICK, relation.getName()).add(this.fieldRole, muc.getRole()).get()).get()).get(), true, false, WriteConcern.SAFE);
		}
		return this;
	}

	@Override
	public MongoRelationMucContext update(JID from, JID to, String state) {
		return null;
	}

	@Override
	public MongoRelationMucContext remove(JID from, JID to) {
		this.config.collection().update(this.buildQuery(to.asStringWithBare()), BasicDBObjectBuilder.start("$pull", BasicDBObjectBuilder.start(this.fieldRoles, BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_JID, from.asStringWithBare()).add(MongoProxyConfig.FIELD_RESOURCE, from.resource()).get()).get()).get(), false, false, WriteConcern.SAFE);
		return this;
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		AggregationOutput output = this.config.collection().aggregate(BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_JID, to.asStringWithBare()).get()).get(), this.aggregateUnwindRoles, this.aggregateUnwindAffiliation, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start().add(this.fieldRoles + "." + MongoProxyConfig.FIELD_JID, from.asStringWithBare()).add(this.fieldRoles + "." + MongoProxyConfig.FIELD_RESOURCE, from.resource()).get()).get(), this.aggregateProject, this.aggregateSort, this.aggregateLimit);
		List<?> result = List.class.cast(output.getCommandResult().get(this.fieldResult));
		return result.isEmpty() ? new NoneRelation(to) : new MongoRelation(DBObject.class.cast(result.get(0)));
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().findOne(this.buildQuery(from.asStringWithBare())));
	}

	@Override
	public Set<JID> whoSubscribedMe(JID from) {
		return new JIDGroup(this.config.asStrings(this.config.collection().findOne(this.buildQuery(from.asStringWithBare())), this.fieldRoles));
	}

	@Override
	public Set<JID> iSubscribedWho(JID from) {
		DBObject match = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start().add(this.fieldRoles + "." + MongoProxyConfig.FIELD_JID, from.asStringWithBare()).add(this.fieldRoles + "." + MongoProxyConfig.FIELD_RESOURCE, from.resource()).get()).get();
		return new MongoJIDGroup(List.class.cast(this.config.collection().aggregate(match, BasicDBObjectBuilder.start("$unwind", "$" + this.fieldRoles).get(), match, BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoProxyConfig.FIELD_JID, "$" + MongoProxyConfig.FIELD_JID).add(MongoProxyConfig.FIELD_RESOURCE, "$" + this.fieldRoles + "." + MongoProxyConfig.FIELD_NICK).get()).get()).getCommandResult().get(this.fieldResult)));
	}

	@Override
	public JID mapping(JID group) {
		AggregationOutput output = this.config.collection().aggregate(BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_JID, group.asStringWithBare()).get()).get(), this.aggregateUnwindRoles, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(this.fieldRoles + "." + MongoProxyConfig.FIELD_NICK, group.resource()).get()).get());
		List<?> result = List.class.cast(output.getCommandResult().get(this.fieldResult));
		return result.isEmpty() ? OfflineJID.OFFLINE : this.build(DBObject.class.cast(result.get(0)));
	}

	private class JIDGroup extends HashSet<JID> {

		private final static long serialVersionUID = 1L;

		public JIDGroup(String[] jids) {
			if (jids == null) {
				return;
			}
			for (String jid : jids) {
				this.add(MongoRelationMucContext.this.jidBuilder.build(jid));
			}
		}
	}

	private class MongoJIDGroup extends HashSet<JID> {

		private final static long serialVersionUID = 1L;

		public MongoJIDGroup(List<?> db) {
			if (db == null) {
				return;
			}
			for (Object each : db) {
				DBObject jid = DBObject.class.cast(each);
				this.add(MongoRelationMucContext.this.jidBuilder.build(MongoRelationMucContext.this.config.asString(jid, MongoProxyConfig.FIELD_JID)).resource(MongoRelationMucContext.this.config.asString(jid, MongoProxyConfig.FIELD_RESOURCE)));
			}
		}
	}

	private class MongoRelations extends HashSet<Relation> {

		private final static long serialVersionUID = 1L;

		private final Map<String, String> affiliations = new HashMap<String, String>();

		public MongoRelations(DBObject db) {
			if (db == null) {
				return;
			}
			this.prepareAffiliations(db).prepareRelations(db);
		}

		protected MongoRelations prepareRelations(DBObject db) {
			DBObject relation = new BasicDBObject();
			for (Object each : List.class.cast(db.get(MongoRelationMucContext.this.fieldRoles))) {
				DBObject role = DBObject.class.cast(each);
				relation.put(MongoRelationMucContext.this.fieldCreator, MongoRelationMucContext.this.config.asString(db, MongoRelationMucContext.this.fieldCreator));
				relation.put(MongoRelationMucContext.this.fieldActivate, MongoRelationMucContext.this.config.asBoolean(db, MongoRelationMucContext.this.fieldActivate));
				relation.put(MongoRelationMucContext.this.fieldAffiliation, this.affiliations.get(role.get(MongoProxyConfig.FIELD_JID)));
				relation.put(MongoRelationMucContext.this.fieldRoles, role);
				super.add(new MongoRelation(relation));
			}
			return this;
		}

		private MongoRelations prepareAffiliations(DBObject db) {
			List<?> affiliations = List.class.cast(db.get(MongoRelationMucContext.this.fieldAffiliations));
			for (Object each : affiliations) {
				DBObject affiliation = DBObject.class.cast(each);
				this.affiliations.put(MongoRelationMucContext.this.config.asString(affiliation, MongoProxyConfig.FIELD_JID), MongoRelationMucContext.this.config.asString(affiliation, MongoRelationMucContext.this.fieldAffiliation));
			}
			return this;
		}
	}

	private class MongoRelation implements RelationMuc {

		private final String jid;

		private final String name;

		private final String creator;

		private final String resource;

		private final String affiliaion;

		private final String role;

		public MongoRelation(DBObject db) {
			this.creator = MongoRelationMucContext.this.config.asString(db, MongoRelationMucContext.this.fieldCreator);
			this.affiliaion = MongoRelationMucContext.this.config.asString(db, MongoRelationMucContext.this.fieldAffiliation);
			DBObject roles = DBObject.class.cast(db.get(MongoRelationMucContext.this.fieldRoles));
			this.jid = MongoRelationMucContext.this.config.asString(roles, MongoProxyConfig.FIELD_JID);
			this.name = MongoRelationMucContext.this.config.asString(roles, MongoProxyConfig.FIELD_NICK);
			this.resource = MongoRelationMucContext.this.config.asString(roles, MongoProxyConfig.FIELD_RESOURCE);
			this.role = MongoRelationMucContext.this.config.asString(roles, MongoRelationMucContext.this.fieldRole);
		}

		public String getJID() {
			return MongoRelationMucContext.this.jidBuilder.build(this.jid + "/" + this.resource).asString();
		}

		public String getName() {
			return this.name;
		}

		public boolean isActivate() {
			return true;
		}

		public String getAffiliaion() {
			return this.creator.equals(this.jid) ? ItemAffiliation.OWNER.toString() : this.affiliaion != null ? this.affiliaion : ItemAffiliation.NONE.toString();
		}

		@Override
		public String getRole() {
			return this.role;
		}

		public Map<String, Object> plus() {
			return MongoRelationMucContext.this.fieldPlus;
		}
	}
}
