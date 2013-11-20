package com.sissi.context;

/**
 * @author kim 2013-11-12
 */
public interface JIDBuilder {

	public JID build(String jid);
	
<<<<<<< HEAD
	public JID build(String user, String resource);
=======
	public JID build(String name, String pass);
	
	public JID build(String name, String pass, String resource);
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
}
