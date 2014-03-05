package com.sissi.ucenter.muc.impl;

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
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.impl.OfflineJID;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.impl.NoneRelation;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年2月11日
 */
public class MongoRelationMucContext implements RelationContext, RelationMucMapping {

	private final String fieldRole = "role";

	private final String fieldRoles = this.fieldRole + "s";

	private final String fieldResult = "result";

	private final String fieldAffiliation = "affiliation";

	private final String fieldAffiliations = this.fieldAffiliation + "s";

	private final Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final DBObject aggregateLimit = BasicDBObjectBuilder.start().add("$limit", 1).get();

	private final DBObject aggregateUnwindRoles = BasicDBObjectBuilder.start().add("$unwind", "$" + this.fieldRoles).get();

	private final DBObject aggregateUnwindAffiliation = BasicDBObjectBuilder.start().add("$unwind", "$" + this.fieldAffiliations).get();

	private final DBObject aggregateSort = BasicDBObjectBuilder.start().add("$sort", BasicDBObjectBuilder.start(this.fieldAffiliation, -1).get()).get();

	private final DBObject aggregateProject = BasicDBObjectBuilder.start().add("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ACTIVATE, "$" + MongoConfig.FIELD_ACTIVATE).add(MongoConfig.FIELD_CREATOR, "$" + MongoConfig.FIELD_CREATOR).add(this.fieldRoles, "$" + this.fieldRoles).add(this.fieldAffiliation, BasicDBObjectBuilder.start().add("$cond", new Object[] { BasicDBObjectBuilder.start("$eq", new String[] { "$" + this.fieldAffiliations + "." + MongoConfig.FIELD_JID, "$" + this.fieldRoles + "." + MongoConfig.FIELD_JID }).get(), "$" + this.fieldAffiliations + "." + this.fieldAffiliation, null }).get()).get()).get();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	public MongoRelationMucContext(MongoConfig config, JIDBuilder jidBuilder) throws Exception {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
	}

	private DBObject buildQuery(String jid) {
		return BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, jid).get();
	}

	@Override
	public MongoRelationMucContext establish(JID from, Relation relation) {
		try {
			DBObject query = this.buildQuery(relation.getJID());
			query.put(this.fieldRoles + "." + MongoConfig.FIELD_JID, from.asStringWithBare());
			query.put(this.fieldRoles + "." + MongoConfig.FIELD_RESOURCE, from.resource());
			// Update
			this.config.collection().update(query, BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(this.fieldRoles + ".$." + this.fieldRole, relation.cast(RelationMuc.class).getRole()).add(this.fieldRoles + ".$." + MongoConfig.FIELD_NICK, relation.getName()).get()).get(), true, false, WriteConcern.SAFE);
		} catch (MongoException e) {
			// Upsert
			this.config.collection().update(this.buildQuery(relation.getJID()), BasicDBObjectBuilder.start().add("$setOnInsert", BasicDBObjectBuilder.start(relation.plus()).add(MongoConfig.FIELD_ACTIVATE, false).add(MongoConfig.FIELD_CREATOR, from.asStringWithBare()).add(this.fieldAffiliations, new DBObject[] { BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(this.fieldAffiliation, ItemAffiliation.OWNER.toString()).get() }).get()).add("$addToSet", BasicDBObjectBuilder.start().add(this.fieldRoles, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_RESOURCE, from.resource()).add(MongoConfig.FIELD_NICK, relation.getName()).add(this.fieldRole, relation.cast(RelationMuc.class).getRole()).get()).get()).get(), true, false, WriteConcern.SAFE);
		}
		return this;
	}

	@Override
	public MongoRelationMucContext update(JID from, JID to, String state) {
		return this;
	}

	@Override
	public MongoRelationMucContext remove(JID from, JID to) {
		this.config.collection().update(this.buildQuery(to.asStringWithBare()), BasicDBObjectBuilder.start("$pull", BasicDBObjectBuilder.start(this.fieldRoles, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_RESOURCE, from.resource()).get()).get()).get(), false, false, WriteConcern.SAFE);
		return this;
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		AggregationOutput output = this.config.collection().aggregate(BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, to.asStringWithBare()).get()).get(), this.aggregateUnwindRoles, this.aggregateUnwindAffiliation, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start().add(this.fieldRoles + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).add(this.fieldRoles + "." + MongoConfig.FIELD_RESOURCE, from.resource()).get()).get(), this.aggregateProject, this.aggregateSort, this.aggregateLimit);
		List<?> result = List.class.cast(output.getCommandResult().get(this.fieldResult));
		return result.isEmpty() ? new NoneRelation(to) : new MongoRelation(DBObject.class.cast(result.get(0)));
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().findOne(this.buildQuery(from.asStringWithBare())));
	}

	@Override
	public Set<JID> whoSubscribedMe(JID from) {
		return new JIDGroup(Extracter.asStrings(this.config.collection().findOne(this.buildQuery(from.asStringWithBare())), this.fieldRoles));
	}

	@Override
	public Set<JID> iSubscribedWho(JID from) {
		DBObject match = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start().add(this.fieldRoles + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).add(this.fieldRoles + "." + MongoConfig.FIELD_RESOURCE, from.resource()).get()).get();
		return new JIDGroup(List.class.cast(this.config.collection().aggregate(match, BasicDBObjectBuilder.start("$unwind", "$" + this.fieldRoles).get(), match, BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_RESOURCE, "$" + this.fieldRoles + "." + MongoConfig.FIELD_NICK).get()).get()).getCommandResult().get(this.fieldResult)));
	}

	@Override
	public JID mapping(JID group) {
		AggregationOutput output = this.config.collection().aggregate(BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get()).get(), this.aggregateUnwindRoles, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(this.fieldRoles + "." + MongoConfig.FIELD_NICK, group.resource()).get()).get());
		List<?> result = List.class.cast(output.getCommandResult().get(this.fieldResult));
		return result.isEmpty() ? OfflineJID.OFFLINE : this.extract(DBObject.class.cast(result.get(0)));
	}

	private JID extract(DBObject db) {
		DBObject roles = Extracter.asDBObject(db, this.fieldRoles);
		return this.jidBuilder.build(Extracter.asString(roles, MongoConfig.FIELD_JID)).resource(Extracter.asString(roles, MongoConfig.FIELD_RESOURCE));
	}

	private class JIDGroup extends HashSet<JID> {

		private final static long serialVersionUID = 1L;

		public JIDGroup(String[] jids) {
			if (jids == null) {
				return;
			}
			for (String jid : jids) {
				super.add(MongoRelationMucContext.this.jidBuilder.build(jid));
			}
		}

		public JIDGroup(List<?> db) {
			if (db == null) {
				return;
			}
			for (Object each : db) {
				DBObject jid = DBObject.class.cast(each);
				super.add(MongoRelationMucContext.this.jidBuilder.build(Extracter.asString(jid, MongoConfig.FIELD_JID)).resource(Extracter.asString(jid, MongoConfig.FIELD_RESOURCE)));
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

		private MongoRelations prepareAffiliations(DBObject db) {
			List<?> affiliations = List.class.cast(db.get(MongoRelationMucContext.this.fieldAffiliations));
			for (Object each : affiliations) {
				DBObject affiliation = DBObject.class.cast(each);
				this.affiliations.put(Extracter.asString(affiliation, MongoConfig.FIELD_JID), Extracter.asString(affiliation, MongoRelationMucContext.this.fieldAffiliation));
			}
			return this;
		}

		private MongoRelations prepareRelations(DBObject db) {
			DBObject relation = new BasicDBObject();
			for (Object each : List.class.cast(db.get(MongoRelationMucContext.this.fieldRoles))) {
				relation.put(MongoConfig.FIELD_CREATOR, Extracter.asString(db, MongoConfig.FIELD_CREATOR));
				DBObject roles = DBObject.class.cast(each);
				relation.put(MongoRelationMucContext.this.fieldRoles, roles);
				relation.put(MongoRelationMucContext.this.fieldAffiliation, this.affiliations.get(roles.get(MongoConfig.FIELD_JID)));
				super.add(new MongoRelation(relation));
			}
			return this;
		}
	}

	private class MongoRelation implements RelationMuc {

		private final String jid;

		private final String name;

		private final String role;

		private final String creator;

		private final String resource;

		private final String affiliaion;

		public MongoRelation(DBObject db) {
			this.creator = Extracter.asString(db, MongoConfig.FIELD_CREATOR);
			this.affiliaion = Extracter.asString(db, MongoRelationMucContext.this.fieldAffiliation);
			DBObject roles = Extracter.asDBObject(db, MongoRelationMucContext.this.fieldRoles);
			this.jid = Extracter.asString(roles, MongoConfig.FIELD_JID);
			this.name = Extracter.asString(roles, MongoConfig.FIELD_NICK);
			this.resource = Extracter.asString(roles, MongoConfig.FIELD_RESOURCE);
			this.role = Extracter.asString(roles, MongoRelationMucContext.this.fieldRole);
		}

		public String getJID() {
			return MongoRelationMucContext.this.jidBuilder.build(this.jid + "/" + this.resource).asString();
		}

		public String getName() {
			return this.name;
		}

		public boolean isActivate() {
			return !ItemAffiliation.OUTCAST.equals(this.affiliaion);
		}

		public String getAffiliation() {
			return this.creator.equals(this.jid) ? ItemAffiliation.OWNER.toString() : ItemAffiliation.parse(this.affiliaion).toString();
		}

		@Override
		public String getRole() {
			return this.role;
		}

		public Map<String, Object> plus() {
			return MongoRelationMucContext.this.fieldPlus;
		}

		@Override
		public <T extends Relation> T cast(Class<T> clazz) {
			return clazz.cast(this);
		}
	}
}
