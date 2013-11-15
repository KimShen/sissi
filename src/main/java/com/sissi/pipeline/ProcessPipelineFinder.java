 package com.sissi.pipeline;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public interface ProcessPipelineFinder {

	public ProcessPipeline find(Protocol protocol);
}
