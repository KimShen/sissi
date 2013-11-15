package com.sissi.pipeline.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-14
 */
public class NothingProcessor implements ProcessPipeline {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public boolean process(JIDContext context, Protocol current) {
		this.log.warn("In nothing processor for " + current + ", please check");
		return false;
	}
}