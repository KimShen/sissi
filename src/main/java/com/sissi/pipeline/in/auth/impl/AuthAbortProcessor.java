package com.sissi.pipeline.in.auth.impl;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.iq.auth.Failure;

/**
 * <abort xmlns='urn:ietf:params:xml:ns:xmpp-sasl'/>
 * 
 * @author kim 2014年1月6日
 */
public class AuthAbortProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.binding() ? true : !context.write(Failure.INSTANCE_ABORTED).write(Stream.closeGraceFully()).close();
	}
}
