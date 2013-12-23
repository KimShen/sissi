package com.sissi.context;

/**
 * @author kim 2013年12月23日
 */
public interface JIDBuilder {

	public JID build(String jid);

	public JID build(String username, String resource);
}