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
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucConfigParam;
import com.sissi.ucenter.muc.MucConfigParser;
import com.sissi.ucenter.muc.MucFinder;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月20日
 */
public class MongoMucConfigBuilder implements MucFinder, MucConfigBuilder {

	private final String fieldMapping = "mapping";

	private final Map<String, Object> emptyConfigs = new HashMap<String, Object>();

	private final Map<String, MucConfigParser> parsers = new HashMap<String, MucConfigParser>();

	private final Map<String, MucConfigArbitrament> arbitraments = new HashMap<String, MucConfigArbitrament>();

	private final DBObject filter = BasicDBObjectBuilder.start().add(MongoConfig.FIELD_CONFIGS, 1).add(MongoConfig.FIELD_CREATOR, 1).add(MongoConfig.FIELD_ACTIVATE, 1).get();

	private final MongoConfig config;

	private final JIDBuilder jidBuilder;

	private final RelationContext relationContext;

	public MongoMucConfigBuilder(MongoConfig config, JIDBuilder jidBuilder, RelationContext relationContext, List<MucConfigParser> parsers, List<MucConfigArbitrament> arbitraments) {
		super();
		this.config = config;
		this.jidBuilder = jidBuilder;
		this.relationContext = relationContext;
		for (MucConfigParser each : parsers) {
			this.parsers.put(each.support(), each);
		}
		for (MucConfigArbitrament each : arbitraments) {
			this.arbitraments.put(each.support(), each);
		}
	}

	@Override
	public MucConfig build(JID group) {
		DBObject db = this.config.collection().findOne(BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get(), this.filter);
		return new MongoMucGroupConfig(group, this.jidBuilder.build(Extracter.asString(db, MongoConfig.FIELD_CREATOR)), Extracter.asDBObject(db, MongoConfig.FIELD_CONFIGS));
	}

	@Override
	public boolean exists(JID group) {
		return this.config.collection().findOne(BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get()) != null;
	}

	private class MucConfigParamImpl implements MucConfigParam {

		private final JID user;

		private final JID group;

		private final JID creator;

		private final DBObject configs;

		public MucConfigParamImpl(JID user, JID group, JID creator, DBObject configs) {
			super();
			this.user = user;
			this.group = group;
			this.creator = creator;
			this.configs = configs;
		}

		public JID user() {
			return this.user;
		}

		public boolean affiliation() {
			return this.affiliation(Extracter.asString(this.configs, MongoConfig.FIELD_AFFILIATION));
		}

		public boolean affiliation(String affiliation) {
			return this.creator() || ItemAffiliation.parse(this.relation().affiliation()).contains(affiliation);
		}

		public boolean hidden(boolean compute) {
			return compute ? !this.creator() && !ItemRole.parse(MongoMucConfigBuilder.this.relationContext.ourRelation(this.user, this.group).cast(RelationMuc.class).role()).contains(ItemRole.MODERATOR) && Extracter.asBoolean(this.configs, MongoConfig.FIELD_HIDDEN) : Extracter.asBoolean(this.configs, MongoConfig.FIELD_HIDDEN);
		}

		@Override
		public boolean creator() {
			return this.user.like(this.creator);
		}

		public boolean activate() {
			return this.creator() || Extracter.asBoolean(this.configs, MongoConfig.FIELD_ACTIVATE, true);
		}

		public RelationMuc relation() {
			return MongoMucConfigBuilder.this.relationContext.ourRelation(this.user, this.group).cast(RelationMuc.class);
		}

		@SuppressWarnings("unchecked")
		public Map<String, Object> configs() {
			return this.configs != null ? this.configs.toMap() : MongoMucConfigBuilder.this.emptyConfigs;
		}
	}

	private class MongoMucGroupConfig implements MucConfig {

		private final JID group;

		private final JID creator;

		private final int[] mapping;

		private final DBObject configs;

		public MongoMucGroupConfig(JID group, JID creator, DBObject configs) {
			super();
			this.group = group;
			this.creator = creator;
			this.configs = configs;
			this.mapping = Extracter.asInts(configs, MongoMucConfigBuilder.this.fieldMapping);
		}

		public <T> T pull(String key, Class<T> clazz) {
			return this.configs != null ? clazz.cast(this.configs.get(key)) : null;
		}

		public MucConfig push(Fields fields) {
			for (Field<?> field : fields) {
				this.push(field);
			}
			return this;
		}

		public MucConfig push(Field<?> field) {
			MucConfigParser parser = MongoMucConfigBuilder.this.parsers.get(field.getName());
			if (parser != null) {
				MongoMucConfigBuilder.this.config.collection().update(BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, this.group.asStringWithBare()).get(), BasicDBObjectBuilder.start("$set", BasicDBObjectBuilder.start(MongoConfig.FIELD_CONFIGS + "." + parser.field(), parser.parse(field)).get()).get());
			}
			return this;
		}

		@Override
		public String mapping(String affiliation) {
			int ordinal = ItemAffiliation.parse(affiliation).ordinal();
			return this.mapping != null && this.mapping.length > ordinal ? ItemRole.toString(this.mapping[ordinal]) : ItemRole.NONE.toString();
		}

		@Override
		public boolean allowed(JID jid, String key, Object value) {
			MucConfigArbitrament arbitrament = MongoMucConfigBuilder.this.arbitraments.get(key);
			return arbitrament != null ? arbitrament.arbitrate(new MucConfigParamImpl(jid, this.group, this.creator, this.configs), value) : false;
		}
	}
}
