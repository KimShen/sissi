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
import com.sissi.ucenter.impl.NoneRelation;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucJIDs;
import com.sissi.ucenter.muc.MucRelationContext;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年2月11日
 */
abstract class MongoRelationMucContext implements MucRelationContext, RelationMucMapping {

	private final Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final DBObject aggregateLimit = BasicDBObjectBuilder.start().add("$limit", 1).get();

	private final DBObject aggregateSort = BasicDBObjectBuilder.start().add("$sort", BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATION, -1).get()).get();

	private final DBObject aggregateProjectMapping = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES, "$" + MongoConfig.FIELD_ROLES).get()).get();

	private final DBObject aggregateGroup = BasicDBObjectBuilder.start("$group", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ID, "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_RESOURCE, BasicDBObjectBuilder.start("$push", "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_RESOURCE).get()).get()).get();

	private final DBObject aggregateGroupRelations = BasicDBObjectBuilder.start("$group", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ID, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_CREATOR, "$" + MongoConfig.FIELD_CREATOR).add(MongoConfig.FIELD_ACTIVATE, "$" + MongoConfig.FIELD_ACTIVATE).add(MongoConfig.FIELD_AFFILIATIONS, "$" + MongoConfig.FIELD_AFFILIATIONS).get()).add(MongoConfig.FIELD_ROLES, BasicDBObjectBuilder.start("$addToSet", "$" + MongoConfig.FIELD_ROLES).get()).get()).get();

	private final DBObject aggregateProjectRelations = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_ID + "." + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_CREATOR, "$" + MongoConfig.FIELD_ID + "." + MongoConfig.FIELD_CREATOR).add(MongoConfig.FIELD_ACTIVATE, "$" + MongoConfig.FIELD_ID + "." + MongoConfig.FIELD_ACTIVATE).add(MongoConfig.FIELD_AFFILIATIONS, "$" + MongoConfig.FIELD_ID + "." + MongoConfig.FIELD_AFFILIATIONS).add(MongoConfig.FIELD_ROLES, "$" + MongoConfig.FIELD_ROLES).get()).get();

	private final DBObject aggregateProjectRelation = BasicDBObjectBuilder.start().add("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_ACTIVATE, "$" + MongoConfig.FIELD_ACTIVATE).add(MongoConfig.FIELD_CREATOR, "$" + MongoConfig.FIELD_CREATOR).add(MongoConfig.FIELD_ROLES, "$" + MongoConfig.FIELD_ROLES).add(MongoConfig.FIELD_AFFILIATION, BasicDBObjectBuilder.start().add("$cond", new Object[] { BasicDBObjectBuilder.start("$eq", new String[] { "$" + MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID }).get(), "$" + MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_AFFILIATION, null }).get()).get()).get();

	private final DBObject aggregateProjectSubscribed = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, "$" + MongoConfig.FIELD_JID).add(MongoConfig.FIELD_RESOURCE, "$" + MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_NICK).get()).get();

	private final DBObject aggregateAffiliationLimit = BasicDBObjectBuilder.start("$limit", 1).get();

	private final DBObject aggregateAffiliationProject = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATION, "$" + MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_AFFILIATION).get()).get();

	private final DBObject entityCountInc = BasicDBObjectBuilder.start(MongoConfig.FIELD_CONFIGS + "." + MongoConfig.FIELD_COUNT, 1).get();

	private final DBObject entityCountDec = BasicDBObjectBuilder.start(MongoConfig.FIELD_CONFIGS + "." + MongoConfig.FIELD_COUNT, -1).get();

	private final MucJIDs emptyJIDs = new EmptyJIDs();

	protected final Set<Relation> emptyRelations = new HashSet<Relation>();

	protected final DBObject aggregateUnwindRoles = BasicDBObjectBuilder.start().add("$unwind", "$" + MongoConfig.FIELD_ROLES).get();

	protected final DBObject aggregateUnwindAffiliation = BasicDBObjectBuilder.start().add("$unwind", "$" + MongoConfig.FIELD_AFFILIATIONS).get();

	protected final String fieldPath = "path";

	protected final JIDBuilder jidBuilder;

	protected final MongoConfig config;

	private final boolean activate;

	protected MucConfigBuilder mucConfigBuilder;

	public MongoRelationMucContext(boolean activate, MongoConfig config, JIDBuilder jidBuilder) throws Exception {
		super();
		this.config = config;
		this.activate = activate;
		this.jidBuilder = jidBuilder;
	}

	public void setMucConfigBuilder(MucConfigBuilder mucConfigBuilder) {
		this.mucConfigBuilder = mucConfigBuilder;
	}

	private MucJIDs extract(DBObject db) {
		return new ShareJIDs(this.jidBuilder.build(Extracter.asString(db, MongoConfig.FIELD_ID)), Extracter.asStrings(db, MongoConfig.FIELD_RESOURCE));
	}

	private ItemAffiliation affiliation(JID from, JID to) {
		AggregationOutput output = this.config.collection().aggregate(BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, to.asStringWithBare()).get()).get(), this.aggregateAffiliationLimit, this.aggregateUnwindAffiliation, BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATIONS + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).get()).get(), this.aggregateAffiliationProject);
		List<?> result = Extracter.asList(output.getCommandResult(), MongoConfig.FIELD_RESULT);
		return result.isEmpty() ? ItemAffiliation.NONE : ItemAffiliation.parse(Extracter.asString(DBObject.class.cast(result.get(0)), MongoConfig.FIELD_AFFILIATION));
	}

	private DBObject buildQuery(String jid) {
		return BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, jid).get();
	}

	private DBObject buildRemove(JID jid, boolean bare) {
		BasicDBObjectBuilder remove = BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, jid.asStringWithBare());
		if (!bare) {
			remove.add(MongoConfig.FIELD_RESOURCE, jid.resource());
		}
		return BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES, remove.get()).get();
	}

	protected DBObject buildMatcher(JID group) {
		return BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get()).get();
	}

	protected MongoRelationMucContext remove(JID from, JID to, boolean bare) {
		this.config.collection().update(this.buildQuery(to.asStringWithBare()), BasicDBObjectBuilder.start().add("$inc", this.entityCountDec).add("$pull", this.buildRemove(from, true)).get(), false, !from.isBare(), WriteConcern.SAFE);
		return this;
	}

	@Override
	public MongoRelationMucContext establish(JID from, Relation relation) {
		try {
			// Update
			this.config.collection().update(BasicDBObjectBuilder.start(MongoConfig.FIELD_ROLES + "." + this.fieldPath, from.asString()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + ".$." + MongoConfig.FIELD_ROLE, relation.cast(RelationMuc.class).role()).add(MongoConfig.FIELD_ROLES + ".$." + MongoConfig.FIELD_NICK, relation.name()).get()).get(), true, false, WriteConcern.SAFE);
		} catch (MongoException e) {
			// Upsert
			this.config.collection().update(this.buildQuery(relation.jid()), BasicDBObjectBuilder.start().add("$setOnInsert", BasicDBObjectBuilder.start(relation.plus()).add(MongoConfig.FIELD_ACTIVATE, this.activate).add(MongoConfig.FIELD_CREATOR, from.asStringWithBare()).add(MongoConfig.FIELD_AFFILIATIONS, new DBObject[] { BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_AFFILIATION, ItemAffiliation.OWNER.toString()).get() }).get()).add("$inc", this.entityCountInc).add("$addToSet", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, from.asStringWithBare()).add(this.fieldPath, from.asString()).add(MongoConfig.FIELD_RESOURCE, from.resource()).add(MongoConfig.FIELD_NICK, relation.name()).add(MongoConfig.FIELD_ROLE, relation.cast(RelationMuc.class).role()).get()).get()).get(), true, false, WriteConcern.SAFE);
		}
		return this;
	}

	@Override
	public MongoRelationMucContext remove(JID from, JID to) {
		return this.remove(from, to, false);
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		AggregationOutput output = this.config.collection().aggregate(this.buildMatcher(to), this.aggregateUnwindRoles, this.aggregateUnwindAffiliation, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_RESOURCE, from.resource()).get()).get(), this.aggregateProjectRelation, this.aggregateSort, this.aggregateLimit);
		List<?> result = Extracter.asList(output.getCommandResult(), MongoConfig.FIELD_RESULT);
		return result.isEmpty() ? new MucNoneRelation(from, to, this.affiliation(from, to), true) : new MongoRelation(DBObject.class.cast(result.get(0)));
	}

	public Set<Relation> ourRelations(JID from, JID to) {
		AggregationOutput output = this.config.collection().aggregate(this.buildMatcher(to), this.aggregateUnwindRoles, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start().add(MongoConfig.FIELD_ROLES + "." + MongoConfig.FIELD_JID, from.asStringWithBare()).get()).get(), this.aggregateGroupRelations, this.aggregateProjectRelations);
		List<?> result = Extracter.asList(output.getCommandResult(), MongoConfig.FIELD_RESULT);
		return result.isEmpty() ? this.emptyRelations : new MongoRelations(DBObject.class.cast(result.get(0)));
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

	protected class MucNoneRelation extends NoneRelation {

		private final static String zero = "0";

		private final boolean mapping;

		private final JID group;

		protected MucNoneRelation(JID jid, ItemAffiliation affiliation) {
			this(jid, null, affiliation, false);
		}

		public MucNoneRelation(JID jid, JID group, ItemAffiliation affiliation, boolean mapping) {
			super(jid, zero);
			super.affiliation(affiliation.toString());
			this.group = group;
			this.mapping = mapping;
		}

		public String role() {
			return this.mapping ? ItemRole.max(MongoRelationMucContext.this.mucConfigBuilder.build(this.group).mapping(this.affiliation()), super.role()) : super.role();
		}
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

	protected class MongoRelations extends HashSet<Relation> {

		private final static long serialVersionUID = 1L;

		private final Map<String, String> affiliations = new HashMap<String, String>();

		protected MongoRelations(DBObject db) {
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

		private boolean noneRole;

		private String affiliation;

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

		public String resource() {
			return this.resource;
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

		public RelationMuc affiliation(String affiliation) {
			this.affiliation = affiliation;
			return this;
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
