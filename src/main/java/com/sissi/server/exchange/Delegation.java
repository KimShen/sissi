package com.sissi.server.exchange;

import java.io.OutputStream;

/**
 * 离线文件代理
 * 
 * @author kim 2014年2月26日
 */
public interface Delegation {

	/**
	 * 分配OutputStream(需要手动关闭)
	 * 
	 * @param sid
	 * @return
	 */
	public OutputStream allocate(String sid);

	/**
	 * 推送
	 * 
	 * @param exchanger
	 * @return
	 */
	public Delegation push(Exchanger exchanger);
}
