package com.sissi.config;


/**
 * @author kim 2013-11-15
 */
public interface MongoConfig extends Config {

	public final static String D_NAME = "D_NAME";

	public final static String C_NAME = "C_NAME";

	public MongoConfig clear();

	public MongoCollection collection();
}
