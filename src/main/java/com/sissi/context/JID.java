package com.sissi.context;

/**
 * @author kim 2013-10-30
 */
public interface JID {
<<<<<<< HEAD
	
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

=======

	public String user();

	public String host();

	public String resource();

	public String user(String user);

	public String host(String host);

	public String resource(String resource);

	public Boolean bare();
	
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	public String asString();

	public String asStringWithBare();
}
