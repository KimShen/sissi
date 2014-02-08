package com.sissi.context;

/**
 * @author kim 2013-10-30
 */
public interface JID {

	public String user();
	
	public boolean user(JID jid);
	
	public boolean user(String jid);

	public String domain();

	public JID domain(String domain);

	public String resource();

	public JID resource(String resource);
	
	public boolean valid();

	public boolean valid(boolean excludeDomain);

	public JID bare();

	public String asString();

	public String asStringWithBare();
}
