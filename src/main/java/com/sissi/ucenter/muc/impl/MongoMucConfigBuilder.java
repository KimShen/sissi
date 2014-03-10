package com.sissi.ucenter.muc.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucConfigParam;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月20日
 */
public class MongoMucConfigBuilder implements MucConfigBuilder {

	private final String fieldMapping = "mapping";

	private final Map<String, MucConfigArbitrament> arbitraments = new HashMap<String, MucConfigArbitrament>();

	private final DBObject filter = BasicDBObjectBuilder.start().add(MongoConfig.FIELD_CONFIGS, 1).add(MongoConfig.FIELD_CREATOR, 1).add(MongoConfig.FIELD_ACTIVATE, 1).get();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	private final RelationContext relationContext;

	public MongoMucConfigBuilder(MongoConfig config, JIDBuilder jidBuilder, RelationContext relationContext, List<MucConfigArbitrament> arbitraments) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
		this.relationContext = relationContext;
		for (MucConfigArbitrament each : arbitraments) {
			this.arbitraments.put(each.support(), each);
		}
	}

	@Override
	public MucConfig build(JID group) {
		DBObject db = this.config.collection().findOne(BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get(), this.filter);
		return new MongoMucGroupConfig(group, this.jidBuilder.build(Extracter.asString(db, MongoConfig.FIELD_CREATOR)), Extracter.asBoolean(db, MongoConfig.FIELD_ACTIVATE, true), Extracter.asDBObject(db, MongoConfig.FIELD_CONFIGS));
	}

	private class MucConfigParamImpl implements MucConfigParam {

		private final JID user;

		private final JID group;

		private final JID creator;

		private final boolean activate;

		private final Map<String, Object> configs;

		public MucConfigParamImpl(JID user, JID group, JID creator, boolean activate, Map<String, Object> configs) {
			super();
			this.user = user;
			this.group = group;
			this.creator = creator;
			this.configs = configs;
			this.activate = activate;
		}

		private String asString(String key) {
			Object value = this.configs.get(key);
			return value != null ? value.toString() : null;
		}

		private boolean asBoolean(String key) {
			Object value = this.configs.get(key);
			return value != null ? Boolean.parseBoolean(value.toString()) : false;
		}

		public JID user() {
			return this.user;
		}

		public boolean level() {
			return this.level(this.asString(MongoConfig.FIELD_AFFILIATION));
		}

		public boolean level(String affiliation) {
			return this.creator() || MongoMucConfigBuilder.this.config.collection().findOne(BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, this.group.asStringWithBare()).add("$or", new DBObject[] { BasicDBObjectBuilder.start(MongoConfig.FIELD_CONFIGS + "." + MongoConfig.FIELD_AFFILIATION, BasicDBObjectBuilder.start("$exists", false).get()).get(), BasicDBObjectBuilder.start(MongoConfig.FIELD_AFFILIATIONS, BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, this.user.asStringWithBare()).add(MongoConfig.FIELD_AFFILIATION, affiliation).get()).get() }).get()) != null || MongoMucConfigBuilder.this.config.collection().findOne(BasicDBObjectBuilder.start().add(MongoConfig.FIELD_JID, this.group.asStringWithBare()).get()) == null;
		}

		public boolean hidden(boolean compute) {
			return compute ? !this.creator() && this.asBoolean(MongoConfig.FIELD_HIDDEN) : this.asBoolean(MongoConfig.FIELD_HIDDEN);
		}

		@Override
		public boolean creator() {
			return this.user.like(this.creator);
		}

		public boolean activate() {
			return this.creator() || this.activate;
		}

		public RelationMuc relation() {
			return MongoMucConfigBuilder.this.relationContext.ourRelation(this.user, this.group).cast(RelationMuc.class);
		}

		public Map<String, Object> configs() {
			return this.configs;
		}
	}

	private class MongoMucGroupConfig implements MucConfig {

		private final JID group;

		private final JID creator;

		private final int[] mapping;

		private final boolean activate;

		private final Map<String, Object> configs;

		public MongoMucGroupConfig(JID group, JID creator, boolean activate, DBObject configs) {
			super();
			this.group = group;
			this.creator = creator;
			this.activate = activate;
			this.configs = Extracter.asMap(configs);
			this.mapping = Extracter.asInts(configs, MongoMucConfigBuilder.this.fieldMapping);
		}

		public Object extract(String key) {
			return this.configs.get(key);
		}

		@Override
		public String mapping(String affiliation) {
			int ordinal = ItemAffiliation.parse(affiliation).ordinal();
			return this.mapping != null && (this.mapping.length - 1) > ordinal ? ItemRole.toString(this.mapping[ordinal]) : ItemRole.NONE.toString();
		}

		@Override
		public boolean allowed(JID jid, String key, Object value) {
			MucConfigArbitrament arbitrament = MongoMucConfigBuilder.this.arbitraments.get(key);
			return arbitrament != null ? arbitrament.arbitrate(new MucConfigParamImpl(jid, this.group, this.creator, this.activate, this.configs), value) : false;
		}
	}
}
