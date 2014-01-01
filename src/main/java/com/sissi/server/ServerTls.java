package com.sissi.server;

import com.sissi.server.impl.NetworkTls;

/**
 * @author kim 2013年12月17日
 */
public interface ServerTls {

	public NetworkTls starttls();
	 
	public Boolean isTls();  
	
}
