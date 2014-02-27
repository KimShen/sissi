package com.sissi.ucenter.relation.muc;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.commons.Extracter;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.MucGroupConfig;
import com.sissi.ucenter.MucGroupContext;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月20日
 */
public class MongoMucGroupContext implements MucGroupContext {

	private final String fieldConfigs = "configs";

	private final String fieldHidden = "hidden";

	private final String fieldMapping = "mapping";

	private final String fieldPassword = "password";

	private final String fieldAffiliation = "affiliation";

	private final DBObject filter = BasicDBObjectBuilder.start().add(this.fieldConfigs, 1).add(MongoConfig.FIELD_CREATOR, 1).get();

	private final MongoConfig config;

	private final RelationContext relationContext;

	public MongoMucGroupContext(MongoConfig config, RelationContext relationContext) {
		super();
		this.config = config;
		this.relationContext = relationContext;
	}

	@Override
	public MucGroupConfig find(JID group) {
		DBObject db = this.config.collection().findOne(BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get(), this.filter);
		return new MongoMucGroupConfig(group, db != null ? DBObject.class.cast(db.get(this.fieldConfigs)) : null, Extracter.asString(db, MongoConfig.FIELD_CREATOR));
	}

	private class MongoMucGroupConfig implements MucGroupConfig {

		private final JID group;

		private final int[] mapping;

		private final String creator;

		private final DBObject configs;

		public MongoMucGroupConfig(JID group, DBObject configs, String creator) {
			super();
			this.group = group;
			this.configs = configs;
			this.creator = creator;
			this.mapping = Extracter.asInts(this.configs, MongoMucGroupContext.this.fieldMapping);
		}

		@Override
		public boolean allowed(String key, Object value) {
			switch (key) {
			case MucGroupConfig.HIDDEN_ROLE: {
				if (value == null) {
					return false;
				}
				JID jid = JID.class.cast(value);
				RelationMuc muc = RelationMuc.class.cast(MongoMucGroupContext.this.relationContext.ourRelation(jid, this.group));
				return Extracter.asBoolean(this.configs, MongoMucGroupContext.this.fieldHidden) && !jid.asStringWithBare().equals(this.creator) && !ItemRole.MODERATOR.equals(ItemRole.NONE.equals(muc.getRole()) ? this.mapping(muc.getAffiliation()) : muc.getRole());
			}
			case MucGroupConfig.HIDDEN_PURE:
				return Extracter.asBoolean(this.configs, MongoMucGroupContext.this.fieldHidden);
			case MucGroupConfig.PASSWORD:
				String password = Extracter.asString(this.configs, MongoMucGroupContext.this.fieldPassword);
				return password == null ? true : value == null ? false : password.equals(value.toString());
			case MucGroupConfig.AFFILIATIONS: {
				RelationMuc muc = RelationMuc.class.cast(MongoMucGroupContext.this.relationContext.ourRelation(JID.class.cast(value), this.group));
				return ItemAffiliation.parse(muc.getAffiliation()).contains(ItemAffiliation.parse(Extracter.asString(this.configs, MongoMucGroupContext.this.fieldAffiliation)));
			}
			}
			return false;
		}

		@Override
		public String mapping(String affiliation) {
			int ordinal = ItemAffiliation.parse(affiliation).ordinal();
			return this.mapping != null && (this.mapping.length - 1) > ordinal ? ItemRole.toString(this.mapping[ordinal]) : ItemRole.NONE.toString();
		}
	}
}
