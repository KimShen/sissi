package com.sissi.server;

/**
 * @author kim 2013年12月17日
 */
public interface ServerTls {

	public boolean startTls(String domain);

	public boolean isTls(String domain);

}
