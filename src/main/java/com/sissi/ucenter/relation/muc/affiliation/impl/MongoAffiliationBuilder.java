package com.sissi.ucenter.relation.muc.affiliation.impl;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.context.JID;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.muc.affiliation.AffiliationBuilder;
import com.sissi.ucenter.relation.muc.affiliation.AffiliationUpdater;

/**
 * 索引策略: {"jid":1,"affiliations.jid":1}
 * @author kim 2014年3月9日
 */
public class MongoAffiliationBuilder implements AffiliationBuilder {

	private final MongoConfig config;

	public MongoAffiliationBuilder(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public AffiliationUpdater build(JID group) {
		return new MongoAffiliationUpdater(group);
	}

	private final class MongoAffiliationUpdater implements AffiliationUpdater {

		private final JID group;

		public MongoAffiliationUpdater(JID group) {
			super();
			this.group = group;
		}

		@Override
		public boolean update(JID jid, String affiliation) {
			try {
				// {"affiliations.$.affiliation":Xxx}
				BasicDBObjectBuilder entity = BasicDBObjectBuilder.start().add(Dictionary.FIELD_AFFILIATIONS + ".$." + Dictionary.FIELD_AFFILIATION, affiliation);
				if (ItemAffiliation.OWNER.equals(affiliation)) {
					// {"creator":jid.bare}
					// 如果为Owner则同时更新创建者
					entity.add(Dictionary.FIELD_CREATOR, jid.asStringWithBare());
				}
				// {"jid":group.bare,"affiliations.jid":jid.bare"},{"$set":...entity...}
				return MongoUtils.success(MongoAffiliationBuilder.this.config.collection().update(BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, this.group.asStringWithBare()).add(Dictionary.FIELD_AFFILIATIONS + "." + Dictionary.FIELD_JID, jid.asStringWithBare()).get(), BasicDBObjectBuilder.start().add("$set", entity.get()).get(), true, false, WriteConcern.SAFE));
			} catch (MongoException e) {
				// {"jid":group.bare},{"$addToSet":{"affiliations":{"jid":Xxx,"affiliation":Xxx}}}
				return MongoUtils.success(MongoAffiliationBuilder.this.config.collection().update(BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, this.group.asStringWithBare()).get(), BasicDBObjectBuilder.start("$addToSet", BasicDBObjectBuilder.start(Dictionary.FIELD_AFFILIATIONS, BasicDBObjectBuilder.start().add(Dictionary.FIELD_JID, jid.asStringWithBare()).add(Dictionary.FIELD_AFFILIATION, affiliation).get()).get()).get(), true, false, WriteConcern.SAFE));
			}
		}
	}
}
