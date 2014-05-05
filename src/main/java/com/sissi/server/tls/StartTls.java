package com.sissi.server.tls;

/**
 * StartTLS
 * 
 * @author kim 2013年12月17日
 */
public interface StartTls {

	/**
	 * 启动StartTLS
	 * 
	 * @param domain
	 * @return 是否启动成功
	 */
	public boolean startTls(String domain);

	/**
	 * 是否已启动StartTLS
	 * 
	 * @param domain
	 * @return
	 */
	public boolean isTls(String domain);

}
