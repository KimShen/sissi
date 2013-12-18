package com.sissi.server;

import javax.net.ssl.SSLContext;

/**
 * @author kim 2013年12月18日
 */
public interface ServerTlsBuilder {

	public SSLContext getSSLContext();
}
