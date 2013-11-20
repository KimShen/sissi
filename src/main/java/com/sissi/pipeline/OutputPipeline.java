package com.sissi.pipeline;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-8
 */
public interface OutputPipeline {

	public boolean write(Protocol protocol);
}