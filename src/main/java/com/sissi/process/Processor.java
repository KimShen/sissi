package com.sissi.process;

import com.sissi.context.Context;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public interface Processor {

	public Protocol process(Context context, Protocol protocol);

	public Boolean isSupport(Protocol protocol);
}
