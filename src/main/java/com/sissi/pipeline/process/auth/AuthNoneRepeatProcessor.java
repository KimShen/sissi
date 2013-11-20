package com.sissi.pipeline.process.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class AuthNoneRepeatProcessor implements ProcessPipeline {

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		return context.access() ? logAndReturn(context) : true;
	}

	private boolean logAndReturn(JIDContext context) {
		this.log.warn("Duplice access for " + context.jid().asStringWithBare());
		return false;
	}
}
