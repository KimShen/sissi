package com.sissi.pipeline.process;

import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class AllMatcher implements ProcessPipelineMatcher {

	@Override
	public Boolean match(Protocol protocol) {
		return true;
	}
}
