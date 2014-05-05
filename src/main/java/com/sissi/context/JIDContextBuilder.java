package com.sissi.context;

/**
 * JIDContext构造器
 * 
 * @author kim 2013年12月23日
 */
public interface JIDContextBuilder {

	public JIDContext build(JID jid, JIDContextParam param);
}