package com.sissi.process.iq;

import com.sissi.process.Processor;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013-10-29
 */
public interface Forker extends Processor {

	public String fork();

	public Type type();
}
