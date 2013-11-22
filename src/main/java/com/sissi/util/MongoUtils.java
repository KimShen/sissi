package com.sissi.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sissi.config.Config;
import com.sissi.config.impl.MongoConfig;

/**
 * @author kim 2013-11-15
 */
public class MongoUtils {

	private final static Log LOG = LogFactory.getLog(MongoUtils.class);

	public static void dropCollection(Config config, MongoClient client) {
		MongoUtils.findCollection(config, client).drop();
	}

	public static DBCollection findCollection(Config config, MongoClient client) {
		String db = config.get(MongoConfig.D_NAME);
		String collection = config.get(MongoConfig.C_NAME);
		LOG.debug("Find " + collection + " on " + db);
		return client.getDB(db).getCollection(collection);
	}

	public static String getSecurityString(DBObject db, String key) {
		if(db == null){
			return null;
		}
		Object value = db.get(key);
		LOG.debug("Find " + key + " and value is " + value + " on " + db.toString());
		return value != null ? value.toString() : null;
	}
}
