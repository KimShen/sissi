package com.sissi.config;

/**
 * @author kim 2013-11-15
 */
public interface MongoConfig extends Config {

	public final static String D_NAME = "D_NAME";

	public final static String C_NAME = "C_NAME";

	public static final String FIELD_JID = "jid";

	public static final String FIELD_NICK = "nick";

	public static final String FIELD_STATE = "state";

	public static final String FIELD_INDEX = "index";

	public static final String FIELD_HIDDEN = "hidden";

	public static final String FIELD_CREATOR = "creator";

	public static final String FIELD_PRIORITY = "priority";

	public static final String FIELD_ACTIVATE = "activate";

	public static final String FIELD_RESOURCE = "resource";

	public static final String FIELD_USERNAME = "username";

	public static final String FIELD_PASSWORD = "password";

	public static final String FIELD_AFFILIATION = "affiliation";

	public MongoConfig clear();

	public MongoCollection collection();
}
