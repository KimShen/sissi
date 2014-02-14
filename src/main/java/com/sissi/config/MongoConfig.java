package com.sissi.config;

import com.mongodb.DBObject;

/**
 * @author kim 2013-11-15
 */
public interface MongoConfig extends Config {

	public final static String D_NAME = "D_NAME";

	public final static String C_NAME = "C_NAME";

	public MongoConfig clear();

	public MongoCollection collection();

	public String asString(DBObject db, String key);
	
	public String[] asStrings(DBObject db, String key);
	
	public int asInteger(DBObject db, String key);

	public boolean asBoolean(DBObject db, String key);
	
	public byte[] asBytes(DBObject db, String key);
}
