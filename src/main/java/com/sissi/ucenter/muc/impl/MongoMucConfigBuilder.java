package com.sissi.ucenter.muc.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年2月20日
 */
public class MongoMucConfigBuilder implements MucConfigBuilder {

	private final String fieldConfigs = "configs";

	private final String fieldHidden = "hidden";

	private final String fieldMapping = "mapping";

	private final String fieldPassword = "password";

	private final String fieldAffiliation = "affiliation";

	private final DBObject filter = BasicDBObjectBuilder.start().add(this.fieldConfigs, 1).add(MongoConfig.FIELD_CREATOR, 1).get();

	private final MongoConfig config;

	private final RelationContext relationContext;

	public MongoMucConfigBuilder(MongoConfig config, RelationContext relationContext) {
		super();
		this.config = config;
		this.relationContext = relationContext;
	}

	@Override
	public MucConfig build(JID group) {
		return null;
		// DBObject db = this.config.collection().findOne(BasicDBObjectBuilder.start(MongoConfig.FIELD_JID, group.asStringWithBare()).get(), this.filter);
		// return new MongoMucGroupConfig(group, db != null ? DBObject.class.cast(db.get(this.fieldConfigs)) : null, Extracter.asString(db, MongoConfig.FIELD_CREATOR));
	}
	//
	// private class MongoMucGroupConfig implements MucConfig {
	//
	// private final JID group;
	//
	// private final int[] mapping;
	//
	// private final String creator;
	//
	// private final DBObject configs;
	//
	// public MongoMucGroupConfig(JID group, DBObject configs, String creator) {
	// super();
	// this.group = group;
	// this.configs = configs;
	// this.creator = creator;
	// this.mapping = Extracter.asInts(this.configs, MongoMucConfigBuilder.this.fieldMapping);
	// }
	//
	// @Override
	// public boolean allowed(String key, Object value) {
	// switch (key) {
	// case MucConfig.HIDDEN: {
	// if (value == null) {
	// return false;
	// }
	// JID jid = JID.class.cast(value);
	// RelationMuc muc = RelationMuc.class.cast(MongoMucConfigBuilder.this.relationContext.ourRelation(jid, this.group));
	// return Extracter.asBoolean(this.configs, MongoMucConfigBuilder.this.fieldHidden) && !jid.asStringWithBare().equals(this.creator) && !ItemRole.MODERATOR.equals(ItemRole.NONE.equals(muc.getRole()) ? this.mapping(muc.getAffiliation()) : muc.getRole());
	// }
	// case MucConfig.IS_HIDDEN_PURE:
	// return Extracter.asBoolean(this.configs, MongoMucConfigBuilder.this.fieldHidden);
	// case MucConfig.PASSWORD:
	// String password = Extracter.asString(this.configs, MongoMucConfigBuilder.this.fieldPassword);
	// return password == null ? true : value == null ? false : password.equals(value.toString());
	// case MucConfig.AFFILIATIONS: {
	// RelationMuc muc = RelationMuc.class.cast(MongoMucConfigBuilder.this.relationContext.ourRelation(JID.class.cast(value), this.group));
	// return ItemAffiliation.parse(muc.getAffiliation()).contains(ItemAffiliation.parse(Extracter.asString(this.configs, MongoMucConfigBuilder.this.fieldAffiliation)));
	// }
	// }
	// return false;
	// }
	//
	// @Override
	// public String mapping(String affiliation) {
	// int ordinal = ItemAffiliation.parse(affiliation).ordinal();
	// return this.mapping != null && (this.mapping.length - 1) > ordinal ? ItemRole.toString(this.mapping[ordinal]) : ItemRole.NONE.toString();
	// }
	// }
}
