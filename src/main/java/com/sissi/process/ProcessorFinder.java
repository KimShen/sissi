package com.sissi.process;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public interface ProcessorFinder {

	public Processor find(Protocol protocol);
}
