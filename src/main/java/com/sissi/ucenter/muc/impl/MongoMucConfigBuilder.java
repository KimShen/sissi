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

	private final String fieldConfigs = "configs";

	private final String fieldMapping = "mapping";

	private final Map<String, MucConfigArbitrament> arbitraments = new HashMap<String, MucConfigArbitrament>();

	private final DBObject filter = BasicDBObjectBuilder.start().add(this.fieldConfigs, 1).add(MongoConfig.FIELD_CREATOR, 1).get();

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
		return new MongoMucGroupConfig(group, this.jidBuilder.build(Extracter.asString(db, MongoConfig.FIELD_CREATOR)), Extracter.asDBObject(db, this.fieldConfigs));
	}

	private class MucConfigParamImpl implements MucConfigParam {

		private JID user;

		private JID group;

		private JID creator;

		private Map<String, Object> configs;

		public MucConfigParamImpl(JID user, JID group, JID creator, Map<String, Object> configs) {
			super();
			this.user = user;
			this.group = group;
			this.creator = creator;
			this.configs = configs;
		}

		@Override
		public boolean creator() {
			return this.user.like(this.creator);
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

		private final Map<String, Object> configs;

		public MongoMucGroupConfig(JID group, JID creator, DBObject configs) {
			super();
			this.group = group;
			this.creator = creator;
			this.configs = Extracter.asMap(configs);
			this.mapping = Extracter.asInts(configs, MongoMucConfigBuilder.this.fieldMapping);
		}

		@Override
		public String mapping(String affiliation) {
			int ordinal = ItemAffiliation.parse(affiliation).ordinal();
			return this.mapping != null && (this.mapping.length - 1) > ordinal ? ItemRole.toString(this.mapping[ordinal]) : ItemRole.NONE.toString();
		}

		@Override
		public boolean allowed(JID jid, String key, Object value) {
			MucConfigArbitrament arbitrament = MongoMucConfigBuilder.this.arbitraments.get(key);
			return arbitrament != null ? arbitrament.arbitrate(new MucConfigParamImpl(jid, this.group, this.creator, this.configs), value) : false;
		}
	}
}
