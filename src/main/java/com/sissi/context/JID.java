package com.sissi.context;

/**
 * @author kim 2013-10-30
 */
public interface JID {

	public String getUser();

	public String getHost();

	public JID setResource(String resource);
	
	public String getResource();

	public JID getBare();

	public String asString();

	public String asStringWithBare();

	public interface JIDBuilder {

		public JID build(String jid);

		public JID build(String username, String resource);
	}
}
