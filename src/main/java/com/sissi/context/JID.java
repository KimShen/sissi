package com.sissi.context;

/**
 * @author kim 2013-10-30
 */
public interface JID {

	public String user();

	public String host();

	public String resource();

	public String user(String user);

	public String host(String host);

	public String resource(String resource);

	public Boolean bare();
	
	public String asString();

	public String asStringWithBare();
}
