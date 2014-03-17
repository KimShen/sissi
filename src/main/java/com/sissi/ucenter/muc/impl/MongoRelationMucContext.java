package com.sissi.ucenter.muc.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import com.sissi.context.impl.ShareJIDs;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.impl.NoneRelation;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucJIDs;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年2月11日
 */
public class MongoRelationMucContext implements RelationContext, RelationMucMapping {

	private final String fieldId = "_id";

	private final String fieldPath = "path";

	private final Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final DBObject aggregateLimit = BasicDBObjectBuilder.start().add("$limit", 1).get();

	private final DBObject aggregateUnwindRoles = BasicDBObjectBuilder.start().add("$unwind", "$" + MongoConfig.FIELD_ROLES).get();

	private final DBObject aggregateUnwindAffiliation = BasicDBObjectBuilder.start().add("$unwind", "$" + MongoConfig.FIELD_AFFILIATIONS).get();

	private final DBObject aggregateSort = BasicDBObjectBuilder.start().add("$sort", BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATION, -1).get()).get();

	private final DBObject aggregateProjectMapping = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES, "$" + MongoConfig.FIELD_ROLES).get()).get();

	private final DBObject aggregateGroup = BasicDBObjectBuilder.start("$group", BasicDBObjectBuilder.start().add(this.fieldId, "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_RESOURCE, BasicDBObjectBuilder.start("$push", "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_RESOURCE).get()).get()).get();

	private final DBObject aggregateProjectRelation = BasicDBObjectBuilder.start().add("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_ACTIVATE, "$" + MongoConfig.FIELD_ACTIVATE).add(MongoConfig.FIELD_CREATOR, "$" + MongoConfig.FIELD_CREATOR).add(MongoConfig.FIELD_ROLES, "$" + MongoConfig.FIELD_ROLES).add(MongoConfig.FIELD_AFFILIATION, BasicDBObjectBuilder.start().add("$cond", new Object[] { BasicDBObjectBuilder.start("$eq", new String[] { "$" + MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID }).get(), "$" + MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_AFFILIATION, null }).get()).get()).get();

	private final DBObject aggregateProjectSubscribed = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_RESOURCE, "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_NICK).get()).get();

	private final DBObject entityCountInc = BasicDBObjectBuilder.start(MongoConfig.FIELD_CONFIGS + "." + MongoConfig.FIELD_COUNT, 1).get();

	private final DBObject entityCountDec = BasicDBObjectBuilder.start(MongoConfig.FIELD_CONFIGS + "." + MongoConfig.FIELD_COUNT, -1).get();

	private final MucJIDs emptyJIDs = new EmptyJIDs();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	private MucConfigBuilder mucConfigBuilder;

	public MongoRelationMucContext(MongoConfig config, JIDBuilder jidBuilder) throws Exception {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
	}

	public void setMucConfigBuilder(MucConfigBuilder mucConfigBuilder) {
		this.mucConfigBuilder = mucConfigBuilder;
	}

	private DBObject buildQuery(String jid) {
		return BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, jid).get();
	}

	private DBObject buildMatcher(JID group) {
		return BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get()).get();
	}

	@Override
	public MongoRelationMucContext establish(JID from, Relation relation) {
		try {
			// Update
			this.config.collection().update(BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES + "." + this.fieldPath, from.asString()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + ".$." + MongoConfig.FIELD_ROLE, relation.cast(RelationMuc.class).role()).add(MongoConfig.FIELD_ROLES + ".$." + MongoConfig.FIELD_NICK, relation.name()).get()).get(), true, false, WriteConcern.SAFE);
		} catch (MongoException e) {
			// Upsert
			this.config.collection().update(this.buildQuery(relation.jid()), BasicDBObjectBuilder.start().add("$setOnInsert", BasicDBObjectBuilder.start(relation.plus()).add(MongoConfig.FIELD_ACTIVATE, false).add(MongoConfig.FIELD_CREATOR, from.asStringWithBare()).add(MongoConfig.FIELD_AFFILIATIONS, new DBObject[] { BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_AFFILIATION, ItemAffiliation.OWNER.toString()).get() }).get()).add("$inc", this.entityCountInc).add("$addToSet", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(this.fieldPath, from.asString()).add(MongoConfig.FIELD_RESOURCE, from.resource()).add(MongoConfig.FIELD_NICK, relation.name()).add(MongoConfig.FIELD_ROLE, relation.cast(RelationMuc.class).role()).get()).get()).get(), true, false, WriteConcern.SAFE);
		}
		return this;
	}

	@Override
	public MongoRelationMucContext update(JID from, JID to, String status) {
		this.config.collection().update(BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES + "." + this.fieldPath, from.asString()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + ".$." + MongoConfig.FIELD_ROLE, status).get()).get(), true, false, WriteConcern.SAFE);
		return this;
	}

	@Override
	public MongoRelationMucContext remove(JID from, JID to) {
		this.config.collection().update(this.buildQuery(to.asStringWithBare()), BasicDBObjectBuilder.start().add("$inc", this.entityCountDec).add("$pull", BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_RESOURCE, from.resource()).get()).get()).get(), false, false, WriteConcern.SAFE);
		return this;
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		AggregationOutput output = this.config.collection().aggregate(this.buildMatcher(to), this.aggregateUnwindRoles, this.aggregateUnwindAffiliation, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_RESOURCE, from.resource()).get()).get(), this.aggregateProjectRelation, this.aggregateSort, this.aggregateLimit);
		List<?> result = Extracter.asList(output.getCommandResult(), MongoConfig.FIELD_RESULT);
		return result.isEmpty() ? new NoneRelation(to) : new MongoRelation(DBObject.class.cast(result.get(0)));
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().findOne(this.buildQuery(from.asStringWithBare())));
	}

	@Override
	public Set<JID> whoSubscribedMe(JID from) {
		return new JIDGroup(Extracter.asList(this.config.collection().findOne(this.buildQuery(from.asStringWithBare())), MongoConfig.FIELD_ROLES));
	}

	@Override
	// With Resource(Nickname)
	public Set<JID> iSubscribedWho(JID from) {
		DBObject match = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_RESOURCE, from.resource()).get()).get();
		return new JIDGroup(Extracter.asList(this.config.collection().aggregate(match, BasicDBObjectBuilder.start("$unwind", "$" + MongoConfig.FIELD_ROLES).get(), match, this.aggregateProjectSubscribed).getCommandResult(), MongoConfig.FIELD_RESULT));
	}

	@Override
	public MucJIDs mapping(JID group) {
		AggregationOutput output = this.config.collection().aggregate(this.buildMatcher(group), this.aggregateUnwindRoles, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_NICK, group.resource()).get()).get(), this.aggregateProjectMapping, this.aggregateGroup);
		List<?> result = Extracter.asList(output.getCommandResult(), MongoConfig.FIELD_RESULT);
		return result.isEmpty() ? this.emptyJIDs : this.extract(DBObject.class.cast(result.get(0)));
	}

	private MucJIDs extract(DBObject db) {
		return new ShareJIDs(this.jidBuilder.build(Extracter.asString(db, this.fieldId)), Extracter.asStrings(db, MongoConfig.FIELD_RESOURCE));
	}

	private class EmptyJIDs implements MucJIDs {

		public JID jid() {
			return OfflineJID.OFFLINE;
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
		public Iterator<JID> iterator() {
			return new EmptyIterator();
		}

		public boolean same(JID jid) {
			return false;
		}

		@Override
		public boolean like(JID jid) {
			return false;
		}

		private class EmptyIterator implements Iterator<JID> {

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
		}
	}

	private class JIDGroup extends HashSet<JID> {

		private final static long serialVersionUID = 1L;

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
			List<?> affiliations = Extracter.asList(db, MongoConfig.FIELD_AFFILIATIONS);
			for (Object each : affiliations) {
				DBObject affiliation = DBObject.class.cast(each);
				this.affiliations.put(Extracter.asString(affiliation, MongoConfig.FIELD_JID), Extracter.asString(affiliation, MongoConfig.FIELD_AFFILIATION));
			}
			return this;
		}

		private MongoRelations prepareRelations(DBObject db) {
			DBObject relation = new BasicDBObject();
			relation.put(MongoConfig.FIELD_JID, Extracter.asString(db, MongoConfig.FIELD_JID));
			relation.put(MongoConfig.FIELD_CREATOR, Extracter.asString(db, MongoConfig.FIELD_CREATOR));
			for (Object each : Extracter.asList(db, MongoConfig.FIELD_ROLES)) {
				DBObject roles = DBObject.class.cast(each);
				relation.put(MongoConfig.FIELD_ROLES, roles);
				relation.put(MongoConfig.FIELD_AFFILIATION, this.affiliations.get(roles.get(MongoConfig.FIELD_JID)));
				super.add(new MongoRelation(relation));
			}
			return this;
		}
	}

	private class MongoRelation implements RelationMuc {

		private final String jid;

		private final String group;

		private final String name;

		private final String creator;

		private final String resource;

		private final String affiliation;

		private boolean noneRole;

		private String role;

		public MongoRelation(DBObject db) {
			this.group = Extracter.asString(db, MongoConfig.FIELD_JID);
			this.creator = Extracter.asString(db, MongoConfig.FIELD_CREATOR);
			this.affiliation = Extracter.asString(db, MongoConfig.FIELD_AFFILIATION);
			DBObject roles = Extracter.asDBObject(db, MongoConfig.FIELD_ROLES);
			this.jid = Extracter.asString(roles, MongoConfig.FIELD_JID);
			this.role = Extracter.asString(roles, MongoConfig.FIELD_ROLE);
			this.name = Extracter.asString(roles, MongoConfig.FIELD_NICK);
			this.resource = Extracter.asString(roles, MongoConfig.FIELD_RESOURCE);
		}

		public String jid() {
			return MongoRelationMucContext.this.jidBuilder.build(this.jid + "/" + this.resource).asString();
		}

		public String name() {
			return this.name;
		}

		public boolean name(String name, boolean allowNull) {
			return name.equals(this.name()) || (this.name() == null && allowNull);
		}

		public boolean outcast() {
			return ItemAffiliation.OUTCAST.equals(this.affiliation);
		}

		public boolean activate() {
			return true;
		}

		public String affiliation() {
			return this.creator.equals(this.jid) ? ItemAffiliation.OWNER.toString() : ItemAffiliation.parse(this.affiliation).toString();
		}

		@Override
		public String role() {
			ItemRole mapping = ItemRole.parse(MongoRelationMucContext.this.mucConfigBuilder.build(MongoRelationMucContext.this.jidBuilder.build(this.group)).mapping(this.affiliation()));
			return this.noneRole ? ItemRole.NONE.toString() : ItemRole.parse(this.role).contains(mapping) ? this.role : mapping.toString();
		}

		public MongoRelation role(String role) {
			this.role = role;
			return this;
		}

		@Override
		public MongoRelation noneRole() {
			this.noneRole = true;
			return this;
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
