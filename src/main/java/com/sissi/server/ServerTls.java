package com.sissi.server;

/**
 * @author kim 2013年12月17日
 */
public interface ServerTls {

	public Boolean startTls(String domain);

	public Boolean isTls(String domain);

}
