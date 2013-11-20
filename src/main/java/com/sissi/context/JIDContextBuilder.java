package com.sissi.context;

/**
 * @author kim 2013-11-19
 */
public interface JIDContextBuilder {

	public JIDContext build(JID jid, JIDContextParam param);
}
