package com.sissi.pipeline.in.auth.impl;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.pipeline.in.auth.AuthCallback;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.iq.auth.Auth;
import com.sissi.protocol.iq.auth.Failure;

/**
 * @author kim 2013-10-24
 */
public class AuthForkProcessor extends ProxyProcessor {

	private final Set<AuthCallback> authCallbacks;

	public AuthForkProcessor(Set<AuthCallback> authCallbacks) {
		super();
		this.authCallbacks = authCallbacks;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Auth auth = protocol.cast(Auth.class);
		for (AuthCallback ac : this.authCallbacks) {
			if (ac.support(auth.getMechanism())) {
				return !ac.auth(auth, context);
			}
		}
		return !context.write(Failure.INSTANCE_INVALIDMECHANISM).write(Stream.closeGraceFully()).close();
	}
}
