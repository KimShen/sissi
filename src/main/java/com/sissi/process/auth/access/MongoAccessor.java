package com.sissi.process.auth.access;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.sissi.process.auth.Accessor;
import com.sissi.process.auth.User;

/**
 * @author kim 2013-11-6
 */
public class MongoAccessor implements Accessor {

	private Log log = LogFactory.getLog(this.getClass());

	private String dbName;

	private String clName;

	private MongoClient mongoClient;

	public MongoAccessor(String dbName, String clName, MongoClient mongoClient) {
		super();
		this.dbName = dbName;
		this.clName = clName;
		this.mongoClient = mongoClient;
	}
	@Override
	public boolean access(User user) {
		BasicDBObject query = new BasicDBObject();
		query.put("jid", user.getUser());
		query.put("password", user.getPass());
		this.log.debug("Query for access: " + query);
		return this.mongoClient.getDB(this.dbName).getCollection(this.clName).count(query) != 0;
	}

}
