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

	/**
	 * 全JID比较(Full JID)
	 * 
	 * @param jid
	 * @return
	 */
	public boolean same(JID jid);

	public boolean same(String jid);

	/**
	 * 裸JID比较(Bare JID)
	 * 
	 * @param jid
	 * @return
	 */
	public boolean like(JID jid);

	public boolean like(String jid);

	/**
	 * JID有效性校验(长度,合法字符)
	 * 
	 * @return
	 */
	public boolean valid();

	public boolean valid(boolean excludeDomain);

	public boolean isBare();

	public boolean isGroup();

	/**
	 * Deep copy without resource
	 * 
	 * @return
	 */
	public JID bare();

	/**
	 * Deep copy
	 * 
	 * @return
	 */
	public JID clone();

	/**
	 * Full JID
	 * 
	 * @return
	 */
	public String asString();

	/**
	 * Bare JID
	 * 
	 * @return
	 */
	public String asStringWithBare();

	public String asString(boolean bare);
}
