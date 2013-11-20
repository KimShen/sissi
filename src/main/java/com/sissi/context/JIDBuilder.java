package com.sissi.context;

/**
 * @author kim 2013-11-12
 */
public interface JIDBuilder {

	public JID build(String jid);
	
	public JID build(String user, String resource);
}
