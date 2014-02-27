package com.sissi.context;

/**
 * @author kim 2013-10-30
 */
public interface JID {

	public String user();

	public String domain();

	public JID domain(String domain);

	public String resource();

	public JID resource(String resource);

	public boolean same(JID jid);

	public boolean same(String jid);

	public boolean like(JID jid);

	public boolean like(String jid);

	public boolean valid();

	public boolean valid(boolean excludeDomain);

	public boolean isBare();

	public boolean isGroup();

	public JID bare();

	public JID clone();

	public String asString();

	public String asStringWithBare();
	
	public String asString(boolean bare);
}
