package com.sissi.ucenter.relation;

import com.mongodb.DBObject;
import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.ucenter.MucGroupConfig;
import com.sissi.ucenter.MucGroupContext;

/**
 * @author kim 2014年2月20日
 */
public class MongoMucGroupContext implements MucGroupContext {

	private final MongoConfig config;

	public MongoMucGroupContext(MongoConfig config) {
		super();
		this.config = config;
	}

	@Override
	public MucGroupConfig find(JID group) {
		return new MongoMucGroupConfig(null);
	}

	private class MongoMucGroupConfig implements MucGroupConfig {

		private final DBObject db;

		public MongoMucGroupConfig(DBObject db) {
			super();
			this.db = db;
		}

		@Override
		public boolean allowed(String key, Object value) {
			return false;
		}

		@Override
		public String mapping(String affiliation) {
			return "member";
		}
	}
}
