package com.sissi.server.exchange;

/**
 * 离线文件接收成功后回调
 * 
 * @author kim 2014年2月27日
 */
public interface Recall {

	/**
	 * @param to JID.bare
	 * @return
	 */
	public Recall call(String to);
}
