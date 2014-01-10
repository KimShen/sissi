package com.sissi.context;

/**
 * @author kim 2013-10-30
 */
public interface JID {

	public String getUser();

	public String getDomain();

	public JID setDomain(String domain);

	public String getResource();

	public JID setResource(String resource);

	/**
	 * Jid without resource
	 * 
	 * @return
	 */
	public JID getBare();

	/**
	 * Jid for string format
	 * 
	 * @return
	 */
	public String asString();

	/**
	 * Jid for string format without resource
	 * 
	 * @return
	 */
	public String asStringWithBare();
}
