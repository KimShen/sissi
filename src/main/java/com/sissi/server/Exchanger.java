package com.sissi.server;

import java.nio.ByteBuffer;

/**
 * @author kim 2013年12月22日
 */
public interface Exchanger {

	public Exchanger write(ByteBuffer bytes);
	
	public Exchanger close();
}
