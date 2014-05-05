package com.sissi.pipeline.in.auth.impl;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.iq.auth.Failure;

/**
 * <failure xmlns='urn:ietf:params:xml:ns:xmpp-sasl'/>
 * 
 * @author kim 2013-10-24
 */
public class AuthFailedProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.write(Failure.INSTANCE_NOTAUTHORIZED).authRetry() ? true : !context.write(Stream.closeGraceFully()).close();
	}
}
