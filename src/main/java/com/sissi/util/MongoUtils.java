package com.sissi.util;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.config.Config;
import com.sissi.config.impl.MongoConfig;

/**
 * @author kim 2013-11-15
 */
public class MongoUtils {

	public static DBCollection findCollection(Config config, MongoClient client) {
		return client.getDB(config.get(MongoConfig.D_NAME)).getCollection(config.get(MongoConfig.C_NAME));
	}

	public static String getSecurityString(DBObject db, String key) {
		Object value = db.get(key);
		return value != null ? value.toString() : null;
	}
}
