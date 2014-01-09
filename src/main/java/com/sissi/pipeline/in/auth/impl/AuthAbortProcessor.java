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
		return !context.isBinding() ? this.abortedAndClose(context) : true;
	}

	private Boolean abortedAndClose(JIDContext context) {
		return !context.reset().write(Failure.INSTANCE_ABORTED).write(Stream.closeGracefully()).close();
	}
}
