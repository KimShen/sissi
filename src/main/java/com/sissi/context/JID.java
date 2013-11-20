package com.sissi.context;

/**
 * @author kim 2013-10-30
 */
public interface JID {
	
	public String getUser();

	public String getHost();

	public String getResource();

	public JID setUser(String user);

	public JID setHost(String host);

	public JID setResource(String resource);
	
	public JID setPriority(Integer priority);
	
	public JID getBare();
	
	public Integer getPriority();
	
	public Boolean isBare();

	public String asString();

	public String asStringWithBare();
}
