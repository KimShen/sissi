package com.sissi.ucenter.relation;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoProxyConfig;
import com.sissi.context.JID;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.MucGroupConfig;
import com.sissi.ucenter.MucGroupContext;

/**
 * @author kim 2014年2月20日
 */
public class MongoMucGroupContext implements MucGroupContext {

	private final String fieldConfigs = "configs";

	private final String fieldMapping = "mapping";

	private final DBObject filter = BasicDBObjectBuilder.start(this.fieldConfigs, 1).get();

	private final MongoConfig config;

	public MongoMucGroupContext(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public MucGroupConfig find(JID group) {
		DBObject db = this.config.collection().findOne(BasicDBObjectBuilder.start(MongoProxyConfig.FIELD_JID, group.asStringWithBare()).get(), this.filter);
		return new MongoMucGroupConfig(db != null ? DBObject.class.cast(db.get(this.fieldConfigs)) : null, this.config.asString(db, MongoProxyConfig.FIELD_CREATOR));
	}

	private class MongoMucGroupConfig implements MucGroupConfig {

		private final DBObject configs;

		private final String creator;

		private final int[] mapping;

		public MongoMucGroupConfig(DBObject configs, String creator) {
			super();
			this.configs = configs;
			this.creator = creator;
			this.mapping = MongoMucGroupContext.this.config.asInts(this.configs, MongoMucGroupContext.this.fieldMapping);
		}

		@Override
		public boolean allowed(String key, Object value) {
			return MongoMucGroupContext.this.config.asBoolean(this.configs, MucGroupConfig.HIDDEN) || JID.class.cast(value).asStringWithBare().equals(this.creator);
		}

		@Override
		public String mapping(String affiliation) {
			int ordinal = ItemAffiliation.parse(affiliation).ordinal();
			return (this.mapping.length - 1) < ordinal ? ItemRole.toString(this.mapping[ordinal]) : ItemRole.NONE.toString();
		}
	}
}
