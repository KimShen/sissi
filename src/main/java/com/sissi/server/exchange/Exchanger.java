package com.sissi.server.exchange;

import java.nio.ByteBuffer;

/**
 * @author kim 2013年12月22日
 */
public interface Exchanger {

	public String getHost();

	public Exchanger write(ByteBuffer bytes);
}
