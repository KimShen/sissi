package com.sissi.ucenter.relation.muc.impl;

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
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDs;
import com.sissi.context.impl.OfflineJID;
import com.sissi.context.impl.ShareJIDs;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.impl.DefaultRelation;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.MucRelationContext;
import com.sissi.ucenter.relation.muc.MucRelationMapping;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 索引策略1: {"affiliations.affiliation":1}</p> 索引策略2: {"jid":1}</p>索引策略3: {"jid":1,"role.path":1}</p>索引策略4: {"affiliations.jid":1}</p>索引策略5: {"jid":1,"affiliations.jid":1}</p>索引策略6: {"roles.jid":1,"roles.resource":1}</p>索引策略7: {"roles.jid":1}</p>索引策略8: {"roles.nick":1}
 * 
 * @author kim 2014年4月25日
 */
abstract class MongoMucRelationContext implements MucRelationContext, MucRelationMapping {

	/**
	 * {"$sort":{"affiliation":-1}}
	 */
	private final DBObject sort = BasicDBObjectBuilder.start("$sort", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATION, -1).get()).get();

	/**
	 * {"$match":{"affiliation":{"$exists":true}}}
	 */
	private final DBObject match = BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATION, BasicDBObjectBuilder.start("$exists", true).get()).get()).get();

	/**
	 * {"$project":{"roles":"$roles"}}
	 */
	private final DBObject projectRoles = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start(Dictionary.FIELD_ROLES, "$" + Dictionary.FIELD_ROLES).get()).get();

	/**
	 * {"$group":{"_id":"$roles.jid","resource":{"$push":"$roles.resource"}}}
	 */
	private final DBObject groupMapping = BasicDBObjectBuilder.start("$group", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ID, "$" + Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_JID).add(Dictionary.FIELD_RESOURCE, BasicDBObjectBuilder.start("$push", "$" + Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_RESOURCE).get()).get()).get();

	/**
	 * {"$group":{"_id":{"jid":"$jid","creator":"$creator","activate":"$activate","affiliations":"$affiliations"},"roles":{"$addToSet":"$roles"}}}
	 */
	private final DBObject groupRelations = BasicDBObjectBuilder.start("$group", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ID, BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, "$" + Dictionary.FIELD_JID).add(Dictionary.FIELD_CREATOR, "$" + Dictionary.FIELD_CREATOR).add(Dictionary.FIELD_ACTIVATE, "$" + Dictionary.FIELD_ACTIVATE).add(Dictionary.FIELD_AFFILIATIONS, "$" + Dictionary.FIELD_AFFILIATIONS).get()).add(Dictionary.FIELD_ROLES, BasicDBObjectBuilder.start("$addToSet", "$" + Dictionary.FIELD_ROLES).get()).get()).get();

	/**
	 * {"$group":{"_id":"$_id","roles":{"$addToSet":"$roles"}}}
	 */
	private final DBObject groupSubscribe = BasicDBObjectBuilder.start("$group", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ID, "$" + Dictionary.FIELD_ID).add(Dictionary.FIELD_ROLES, BasicDBObjectBuilder.start("$addToSet", "$" + Dictionary.FIELD_ROLES).get()).get()).get();

	/**
	 * {"$project":{"jid":"$_id.jid","creator":"$_id.creator","activate":"$_id.activate","affiliations":"$_id.affiliations","roles":"$roles"}}
	 */
	private final DBObject projectRelations = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, "$" + Dictionary.FIELD_ID + "." + Dictionary.FIELD_JID).add(Dictionary.FIELD_CREATOR, "$" + Dictionary.FIELD_ID + "." + Dictionary.FIELD_CREATOR).add(Dictionary.FIELD_ACTIVATE, "$" + Dictionary.FIELD_ID + "." + Dictionary.FIELD_ACTIVATE).add(Dictionary.FIELD_AFFILIATIONS, "$" + Dictionary.FIELD_ID + "." + Dictionary.FIELD_AFFILIATIONS).add(Dictionary.FIELD_ROLES, "$" + Dictionary.FIELD_ROLES).get()).get();

	/**
	 * {"$project":{"jid":"$jid","activate":"$activate","creator":"$creator","roles":"$roles","affiliation":{"$cond":[{"$eq":["$affiliations.jid","$roles.jid"]},"$affiliations.affiliation",null]}}}
	 */
	private final DBObject projectRelation = BasicDBObjectBuilder.start().add("$project", BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, "$" + Dictionary.FIELD_JID).add(Dictionary.FIELD_ACTIVATE, "$" + Dictionary.FIELD_ACTIVATE).add(Dictionary.FIELD_CREATOR, "$" + Dictionary.FIELD_CREATOR).add(Dictionary.FIELD_ROLES, "$" + Dictionary.FIELD_ROLES).add(Dictionary.FIELD_AFFILIATION, BasicDBObjectBuilder.start().add("$cond", new Object[] { BasicDBObjectBuilder.start("$eq", new String[] { "$" + Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_JID, "$" + Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_JID }).get(), "$" + Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_AFFILIATION, null }).get()).get()).get();

	/**
	 * {"$project":{"affiliation":"$affiliations.affiliation"}}
	 */
	private final DBObject projectAffiliations = BasicDBObjectBuilder.start("$project", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATION, "$" + Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_AFFILIATION).get()).get();

	/**
	 * {"configs.maxusers":1}
	 */
	private final DBObject countIncMaxUsers = BasicDBObjectBuilder.start(Dictionary.FIELD_CONFIGS + "." + Dictionary.FIELD_MAXUSERS, 1).get();

	/**
	 * {"configs.maxusers":-1}
	 */
	private final DBObject countDecMaxUsers = BasicDBObjectBuilder.start(Dictionary.FIELD_CONFIGS + "." + Dictionary.FIELD_MAXUSERS, -1).get();

	private final JIDs jids = new EmptyJIDs();

	private final Set<JID> jidset = new HashSet<JID>();

	protected final Set<Relation> relations = new HashSet<Relation>();

	protected final Map<String, Object> plus = Collections.unmodifiableMap(new HashMap<String, Object>());

	/**
	 * {"$limit":1}
	 */
	protected final DBObject limit = BasicDBObjectBuilder.start("$limit", 1).get();

	/**
	 * {"$unwind":"$roles"}
	 */
	protected final DBObject unwindRoles = BasicDBObjectBuilder.start().add("$unwind", "$" + Dictionary.FIELD_ROLES).get();

	/**
	 * {"$unwind":"$affiliations"}
	 */
	protected final DBObject unwindAffiliation = BasicDBObjectBuilder.start().add("$unwind", "$" + Dictionary.FIELD_AFFILIATIONS).get();

	private final int[] mapping;

	private final VCardContext vcardContext;

	protected final MongoConfig config;

	protected final JIDBuilder jidBuilder;

	public MongoMucRelationContext(String mapping, MongoConfig config, JIDBuilder jidBuilder, VCardContext vcardContext) throws Exception {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
		this.vcardContext = vcardContext;
		String[] mappings = mapping.split(",");
		this.mapping = new int[mappings.length];
		for (int index = 0; index < mappings.length; index++) {
			this.mapping[index] = Integer.valueOf(mappings[index]);
		}
	}

	/**
	 * {"jid":jid}
	 * 
	 * @param jid
	 * @return
	 */
	protected DBObject buildQuery(String jid) {
		return BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, jid).get();
	}

	/**
	 * {"$match":{"jid":group.bare}}
	 * 
	 * @param group
	 * @return
	 */
	protected DBObject buildMatcher(JID group) {
		return BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_JID, group.asStringWithBare()).get()).get();
	}

	/**
	 * 如果岗位 = NONE, 则同时删除Affiliations.jid, Informaitons.jid
	 * 
	 * @param from
	 * @param to
	 * @param bare
	 * @return
	 */
	protected MongoMucRelationContext remove(JID from, JID to, boolean bare) {
		// {"roles":bare == true ? jid:from.bare : path:from}
		DBObject remove = BasicDBObjectBuilder.start(Dictionary.FIELD_ROLES, bare ? BasicDBObjectBuilder.start(Dictionary.FIELD_JID, from.asStringWithBare()).get() : BasicDBObjectBuilder.start(Dictionary.FIELD_PATH, from.asString()).get()).get();
		if (ItemAffiliation.NONE.equals(this.ourRelation(from, to).cast(MucRelation.class).affiliation())) {
			// {"affiliations.jid":from.bare}
			// {"informaitons.jid":from.bare}
			remove.put(Dictionary.FIELD_AFFILIATIONS, BasicDBObjectBuilder.start(Dictionary.FIELD_JID, from.asStringWithBare()).get());
			remove.put(Dictionary.FIELD_INFORMATIONS, BasicDBObjectBuilder.start(Dictionary.FIELD_JID, from.asStringWithBare()).get());
		}
		// {"jid":to.bare,"role.path":from}, {"$inc":{"configs.maxusers":-1}, "$pull":...remove...}
		this.config.collection().update(BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, to.asStringWithBare()).add(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_PATH, from.asString()).get(), BasicDBObjectBuilder.start().add("$inc", this.countDecMaxUsers).add("$pull", remove).get(), false, false, WriteConcern.SAFE);
		return this;
	}

	private ItemAffiliation affiliation(JID from, JID to) {
		return this.affiliation(from, to, ItemAffiliation.NONE);
	}

	/**
	 * 获取岗位
	 * 
	 * @param from
	 * @param to
	 * @param def 默认值
	 * @return
	 */
	private ItemAffiliation affiliation(JID from, JID to, ItemAffiliation def) {
		// {"$match":{"jid":to.bare}},{"$unwind":"$affiliations"},{"$match":{"affiliations.jid":from.bare}},{"$project":{"affiliation":"$affiliations.affiliation"}}
		AggregationOutput output = this.config.collection().aggregate(BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_JID, to.asStringWithBare()).get()).get(), this.unwindAffiliation, BasicDBObjectBuilder.start("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_JID, from.asStringWithBare()).get()).get(), this.projectAffiliations);
		List<?> result = MongoUtils.asList(output.getCommandResult(), Dictionary.FIELD_RESULT);
		return result.isEmpty() ? def : ItemAffiliation.parse(MongoUtils.asString(DBObject.class.cast(result.get(0)), Dictionary.FIELD_AFFILIATION).toString());
	}

	/*
	 * 1, 假设已为房客尝试$更新角色昵称和岗位昵称</p>2, 如果已存在岗位则更新岗位昵称并AddToSet角色</p>3, 如果不存在岗位则AddToSet岗位和角色并SetOnInsert房间基本信息
	 * 
	 * @see com.sissi.ucenter.relation.RelationContext#establish(com.sissi.context.JID, com.sissi.ucenter.relation.Relation)
	 */
	@Override
	public MongoMucRelationContext establish(JID from, Relation relation) {
		try {
			// {"jid":relation.jid,"roles.path":from.asString}, {"$set":{"roles.nick":relation.name}}
			if (MongoUtils.effect(this.config.collection().update(BasicDBObjectBuilder.start(this.buildQuery(relation.jid()).toMap()).add(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_PATH, from.asString()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ROLES + ".$." + Dictionary.FIELD_NICK, relation.name()).get()).get(), true, false, WriteConcern.SAFE))) {
				// The positional $ operator cannot be used for queries which traverse more than one array
				// https://jira.mongodb.org/browse/SERVER-13509
				// {"jid":relation.jid,"affiliations.jid":from.bare}, {"$set":{"affiliations.nick":relation.name}}
				this.config.collection().update(BasicDBObjectBuilder.start(this.buildQuery(relation.jid()).toMap()).add(Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_JID, from.asStringWithBare()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start().add(Dictionary.FIELD_AFFILIATIONS + ".$." + Dictionary.FIELD_NICK, relation.name()).get()).get());
			}
		} catch (MongoException e) {
			String role = ItemRole.toString(this.mapping[this.vcardContext.exists(this.jidBuilder.build(relation.jid())) ? ItemAffiliation.parse(relation.cast(MucRelation.class).affiliation()).ordinal() : ItemAffiliation.OWNER.ordinal()]);
			// 如果已存在岗位(新增用户)
			if (this.affiliation(from, this.jidBuilder.build(relation.jid()), null) != null) {
				DBObject query = this.buildQuery(relation.jid());
				query.put(Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_JID, from.asStringWithBare());
				// {"jid":jid,"affiliations.jid":from.bare}, {"$inc":{"configs.maxusers":1},"$set":{"affiliations.nick":relation.name,"$addToSet":{"roles":全部属性}}
				this.config.collection().update(query, BasicDBObjectBuilder.start().add("$inc", this.countIncMaxUsers).add("$set", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATIONS + ".$." + Dictionary.FIELD_NICK, relation.name()).get()).add("$addToSet", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ROLES, BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, from.asStringWithBare()).add(Dictionary.FIELD_PATH, from.asString()).add(Dictionary.FIELD_RESOURCE, from.resource()).add(Dictionary.FIELD_NICK, relation.name()).add(Dictionary.FIELD_ROLE, role).add(Dictionary.FIELD_ACTIVATE, System.currentTimeMillis()).get()).get()).get(), true, false, WriteConcern.SAFE);
			} else {
				// {"jid":jid}, {"$setOnInsert", {...plus...,"configs.activate":true,"creator":from.bare,"$inc":{"configs.maxusers":1},"$addToSet":{"affiliations":...全部属性...,"roles":...全部属性...}
				this.config.collection().update(this.buildQuery(relation.jid()), BasicDBObjectBuilder.start().add("$setOnInsert", BasicDBObjectBuilder.start(relation.plus()).add(Dictionary.FIELD_CONFIGS + "." + Dictionary.FIELD_ACTIVATE, false).add(Dictionary.FIELD_CREATOR, from.asStringWithBare()).get()).add("$inc", this.countIncMaxUsers).add("$addToSet", BasicDBObjectBuilder.start().add(Dictionary.FIELD_AFFILIATIONS, BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, from.asStringWithBare()).add(Dictionary.FIELD_AFFILIATION, relation.cast(MucRelation.class).affiliation()).add(Dictionary.FIELD_NICK, relation.name()).get()).add(Dictionary.FIELD_ROLES, BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, from.asStringWithBare()).add(Dictionary.FIELD_PATH, from.asString()).add(Dictionary.FIELD_RESOURCE, from.resource()).add(Dictionary.FIELD_NICK, relation.name()).add(Dictionary.FIELD_ROLE, role).add(Dictionary.FIELD_ACTIVATE, System.currentTimeMillis()).get()).get()).get(), true, false, WriteConcern.SAFE);
			}
		}
		return this;
	}

	/*
	 * 如果岗位 = NONE, 则同时删除Affiliations.jid, Informaitons.jid
	 * 
	 * @see com.sissi.ucenter.relation.RelationContext#remove(com.sissi.context.JID, com.sissi.context.JID)
	 */
	@Override
	public MongoMucRelationContext remove(JID from, JID to) {
		return this.remove(from, to, false);
	}

	/*
	 * {"$match":{"jid":group.bare}}, {"$unwind":"$roles"}, {"$unwind":"$affiliations"}, {"$match":{"roles.jid":Xxx,"roles.resource":Xxx}}, {"$project":{"jid":"$jid","activate":"$activate","creator":"$creator","roles":"$roles","affiliation":{"$cond":[{"$eq":["$affiliations.jid","$roles.jid"]},"$affiliations.affiliation",null]}}}, {"$match":{"affiliation":{"$exists":true}}}, {"$limit":1}
	 * 
	 * @see com.sissi.ucenter.relation.RelationContext#ourRelation(com.sissi.context.JID, com.sissi.context.JID)
	 */
	@Override
	public Relation ourRelation(JID from, JID to) {
		AggregationOutput output = this.config.collection().aggregate(this.buildMatcher(to), this.unwindRoles, this.unwindAffiliation, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_JID, from.asStringWithBare()).add(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_RESOURCE, from.resource()).get()).get(), this.projectRelation, this.match, this.sort, this.limit);
		List<?> result = MongoUtils.asList(output.getCommandResult(), Dictionary.FIELD_RESULT);
		return result.isEmpty() ? new NoneRelation(from, to, this.affiliation(from, to)) : new MongoRelation(DBObject.class.cast(result.get(0)));
	}

	/*
	 * {"$match":{"jid":group.bare}}, {"$unwind":"$roles"}, {"$match":{"roles.jid":Xxx}}, {"$group":{"_id":{"jid":"$jid","creator":"$creator","activate":"$activate","affiliations":"$affiliations"},"roles":{"$addToSet":"$roles"}}}, {"$project":{"jid":"$_id.jid","creator":"$_id.creator","activate":"$_id.activate","affiliations":"$_id.affiliations","roles":"$roles"}}
	 * 
	 * @see com.sissi.ucenter.relation.muc.MucRelationContext#ourRelations(com.sissi.context.JID, com.sissi.context.JID)
	 */
	public Set<Relation> ourRelations(JID from, JID to) {
		AggregationOutput output = this.config.collection().aggregate(this.buildMatcher(to), this.unwindRoles, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start().add(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_JID, from.asStringWithBare()).get()).get(), this.groupRelations, this.projectRelations);
		List<?> result = MongoUtils.asList(output.getCommandResult(), Dictionary.FIELD_RESULT);
		return result.isEmpty() ? this.relations : new MongoRelations(DBObject.class.cast(result.get(0)));
	}

	/*
	 * {"jid":jid}
	 * 
	 * @see com.sissi.ucenter.relation.RelationContext#myRelations(com.sissi.context.JID)
	 */
	@Override
	public Set<Relation> myRelations(JID from) {
		return new MongoRelations(this.config.collection().findOne(this.buildQuery(from.asStringWithBare())));
	}

	/*
	 * {"$match":{"jid":group.bare}}, {"$project":{"roles":"$roles"}}, {"$unwind":"$roles"}, {"$group":{"_id":"$_id","roles":{"$addToSet":"$roles"}}}
	 * 
	 * @see com.sissi.ucenter.relation.RelationContext#whoSubscribedMe(com.sissi.context.JID)
	 */
	@Override
	public Set<JID> whoSubscribedMe(JID from) {
		AggregationOutput output = this.config.collection().aggregate(this.buildMatcher(from), this.projectRoles, this.unwindRoles, this.groupSubscribe);
		List<?> result = MongoUtils.asList(output.getCommandResult(), Dictionary.FIELD_RESULT);
		return result.isEmpty() ? this.jidset : new JIDGroup(MongoUtils.asList(DBObject.class.cast(result.get(0)), Dictionary.FIELD_ROLES));
	}

	@Override
	public JIDs mapping(JID group) {
		// {"$match":{"jid":group.bare}}, {"$unwind":"$roles"}, {"$match":{"roles.nick":Xxx}}, {"$project":{"roles":"$roles"}}, {"$group":{"_id":"$roles.jid","resource":{"$push":"$roles.resource"}}}
		AggregationOutput output = this.config.collection().aggregate(this.buildMatcher(group), this.unwindRoles, BasicDBObjectBuilder.start().add("$match", BasicDBObjectBuilder.start(Dictionary.FIELD_ROLES + "." + Dictionary.FIELD_NICK, group.resource()).get()).get(), this.projectRoles, this.groupMapping);
		List<?> result = MongoUtils.asList(output.getCommandResult(), Dictionary.FIELD_RESULT);
		return result.isEmpty() ? this.jids : this.extract(DBObject.class.cast(result.get(0)));
	}

	private JIDs extract(DBObject db) {
		return new ShareJIDs(this.jidBuilder.build(MongoUtils.asString(db, Dictionary.FIELD_ID)), MongoUtils.asStrings(db, Dictionary.FIELD_RESOURCE));
	}

	/**
	 * 不存在订阅关系
	 * 
	 * @author kim 2014年4月25日
	 */
	protected class NoneRelation extends DefaultRelation {

		private final static String zero = "0";

		protected NoneRelation(JID jid, ItemAffiliation affiliation) {
			this(jid, OfflineJID.OFFLINE, affiliation);
		}

		/**
		 * 1, 岗位 = None则房间存在性校验 2, 房间不存在岗位 = Owner 3, 房间存在岗位 = Affiliation
		 * 
		 * @param jid
		 * @param group
		 * @param affiliation
		 */
		protected NoneRelation(JID jid, JID group, ItemAffiliation affiliation) {
			super(jid, zero);
			super.affiliation(ItemAffiliation.NONE.equals(affiliation) ? MongoMucRelationContext.this.vcardContext.exists(group) ? affiliation.toString() : ItemAffiliation.OWNER.toString() : affiliation.toString());
		}

		/*
		 * 当前角色和映射角色取最大值
		 * 
		 * @see com.sissi.ucenter.relation.impl.DefaultRelation#role()
		 */
		public String role() {
			return ItemRole.max(ItemRole.toString(MongoMucRelationContext.this.mapping[ItemAffiliation.parse(this.affiliation()).ordinal()]), super.role());
		}
	}

	private class EmptyJIDs implements JIDs {

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

	protected class JIDGroup extends HashSet<JID> {

		private final static long serialVersionUID = 1L;

		protected JIDGroup(List<?> db) {
			if (db == null) {
				return;
			}
			for (Object each : db) {
				DBObject jid = DBObject.class.cast(each);
				super.add(MongoMucRelationContext.this.jidBuilder.build(MongoUtils.asString(jid, Dictionary.FIELD_JID)).resource(MongoUtils.asString(jid, Dictionary.FIELD_RESOURCE)));
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

		/**
		 * 拆分JID-岗位
		 * 
		 * @param db
		 * @return
		 */
		private MongoRelations prepareAffiliations(DBObject db) {
			List<?> affiliations = MongoUtils.asList(db, Dictionary.FIELD_AFFILIATIONS);
			for (Object each : affiliations) {
				DBObject affiliation = DBObject.class.cast(each);
				this.affiliations.put(MongoUtils.asString(affiliation, Dictionary.FIELD_JID), MongoUtils.asString(affiliation, Dictionary.FIELD_AFFILIATION));
			}
			return this;
		}

		private MongoRelations prepareRelations(DBObject db) {
			DBObject relation = new BasicDBObject();
			// 恒定信息: JID, 创建者
			relation.put(Dictionary.FIELD_JID, MongoUtils.asString(db, Dictionary.FIELD_JID));
			relation.put(Dictionary.FIELD_CREATOR, MongoUtils.asString(db, Dictionary.FIELD_CREATOR));
			// 加载Roles
			for (Object each : MongoUtils.asList(db, Dictionary.FIELD_ROLES)) {
				DBObject roles = DBObject.class.cast(each);
				relation.put(Dictionary.FIELD_ROLES, roles);
				// 加载岗位
				relation.put(Dictionary.FIELD_AFFILIATION, this.affiliations.get(roles.get(Dictionary.FIELD_JID)));
				super.add(new MongoRelation(relation));
			}
			return this;
		}
	}

	private class MongoRelation implements MucRelation {

		private final String jid;

		private final String creator;

		private final String resource;

		private boolean noneRole;

		private boolean forceRole;

		private boolean forceAffiliation;

		private String affiliation;

		private String role;

		private String name;

		public MongoRelation(DBObject db) {
			this.creator = MongoUtils.asString(db, Dictionary.FIELD_CREATOR);
			this.affiliation = MongoUtils.asString(db, Dictionary.FIELD_AFFILIATION);
			DBObject roles = MongoUtils.asDBObject(db, Dictionary.FIELD_ROLES);
			this.jid = MongoUtils.asString(roles, Dictionary.FIELD_JID);
			this.role = MongoUtils.asString(roles, Dictionary.FIELD_ROLE);
			this.name = MongoUtils.asString(roles, Dictionary.FIELD_NICK);
			this.resource = MongoUtils.asString(roles, Dictionary.FIELD_RESOURCE);
		}

		/*
		 * 包含资源的真实JID
		 * 
		 * @see com.sissi.ucenter.relation.Relation#jid()
		 */
		public String jid() {
			return MongoMucRelationContext.this.jidBuilder.build(this.jid + "/" + this.resource).asString();
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

		/*
		 * 如果使用强制岗位则返回当前岗位, 否则比较是否为创建者并返回Owner
		 * 
		 * @see com.sissi.ucenter.relation.muc.MucRelation#affiliation()
		 */
		public String affiliation() {
			return this.forceAffiliation ? ItemAffiliation.parse(this.affiliation).toString() : this.creator.equals(this.jid) ? ItemAffiliation.OWNER.toString() : ItemAffiliation.parse(this.affiliation).toString();
		}

		public MucRelation affiliation(String affiliation) {
			this.affiliation = affiliation;
			return this;
		}

		public MucRelation affiliation(String affiliation, boolean force) {
			this.forceAffiliation = force;
			return this.affiliation(affiliation);
		}

		/*
		 * 如果使用强制角色则返回当前角色, 如果NoneRole则返回None, 否则使用岗位-角色映射与当前角色的最大值
		 * 
		 * @see com.sissi.ucenter.relation.muc.MucRelation#role()
		 */
		@Override
		public String role() {
			if (this.forceRole) {
				return ItemRole.parse(this.role).toString();
			}
			String role = ItemRole.toString(MongoMucRelationContext.this.mapping[ItemAffiliation.parse(this.affiliation()).ordinal()]);
			return this.noneRole ? ItemRole.NONE.toString() : ItemRole.max(this.role, role);
		}

		public MongoRelation role(String role) {
			this.role = role;
			return this;
		}

		public MongoRelation role(String role, boolean force) {
			this.forceRole = force;
			return this.role(role);
		}

		@Override
		public MongoRelation noneRole() {
			this.noneRole = true;
			return this;
		}

		public Map<String, Object> plus() {
			return MongoMucRelationContext.this.plus;
		}

		@Override
		public <T extends Relation> T cast(Class<T> clazz) {
			return clazz.cast(this);
		}
	}
}
