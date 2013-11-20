package com.sissi.pipeline.process.iq.result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class IQResultMatcher implements ProcessPipelineMatcher {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public Boolean match(Protocol protocol) {
		this.log.info("IQResultMatcher will match all Protocol");
		return true;
	}
}
