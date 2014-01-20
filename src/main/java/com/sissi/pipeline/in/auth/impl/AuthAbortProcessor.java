package com.sissi.pipeline.in.auth.impl;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.iq.auth.Failure;

/**
 * @author kim 2014年1月6日
 */
public class AuthAbortProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		// Ignore if already binding
		return context.isBinding() ? true : !context.write(Failure.INSTANCE_ABORTED).write(Stream.close()).close();
	}
}
