package com.sisi.process;

import com.sisi.context.Context;
import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public interface Processor {

	public Protocol process(Context context, Protocol protocol);
	
	public Boolean isSupport(Protocol protocol);
}
