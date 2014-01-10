package com.sissi.config;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author kim 2013-11-15
 */
public interface MongoConfig extends Config {

	public final static String D_NAME = "DB_NAME";

	public final static String C_NAME = "CL_NAME";

	/**
	 * Clear without drop
	 * 
	 * @return
	 */
	public MongoConfig clear();

	public DBCollection collection();

	/**
	 * Get string value
	 * 
	 * @param db
	 * @param key
	 * @return
	 */
	public String asString(DBObject db, String key);

	/**
	 * Get boolean value, return false if null
	 * 
	 * @param db
	 * @param key
	 * @return
	 */
	public Boolean asBoolean(DBObject db, String key);
}
