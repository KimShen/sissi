package com.sissi.context;

/**
 * @author kim 2013年12月23日
 */
public interface JIDBuilder {

	/**
	 * Build 4 parse
	 * 
	 * @param jid
	 * @return
	 */
	public JID build(String jid);

	/**
	 * Build 4 wrap
	 * 
	 * @param username
	 * @param resource
	 * @return
	 */
	public JID build(String username, String resource);
}