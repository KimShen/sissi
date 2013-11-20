package com.sissi.pipeline;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public interface ProcessPipelineMatcher {

	public Boolean match(Protocol protocol);

}
