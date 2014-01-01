package com.sissi.server;

import com.sissi.server.impl.NetworkTls2;

/**
 * @author kim 2013年12月17日
 */
public interface ServerTls2 {

	public NetworkTls2 starttls();
	 
	public Boolean isTls();  
	
}
