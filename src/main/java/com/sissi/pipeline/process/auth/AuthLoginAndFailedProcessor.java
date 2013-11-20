package com.sissi.pipeline.process.auth;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.auth.Failure;

/**
 * @author kim 2013-10-24
 */
public class AuthLoginAndFailedProcessor implements ProcessPipeline {

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		context.write(Failure.INSTANCE);
		return false;
	}
}
