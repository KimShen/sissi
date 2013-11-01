package com.sisi.process.iq;

import com.sisi.process.Processor;
import com.sisi.protocol.Protocol.Type;

/**
 * @author kim 2013-10-29
 */
public interface Forker extends Processor {

	public String fork();

	public Type type();
}
