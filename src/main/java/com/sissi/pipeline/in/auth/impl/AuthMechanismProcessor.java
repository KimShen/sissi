package com.sissi.pipeline.in.auth.impl;

import java.util.HashMap;
import java.util.Map;
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
public class AuthMechanismProcessor extends ProxyProcessor {

	private final Map<String, AuthCallback> authCallbacks = new HashMap<String, AuthCallback>();

	public AuthMechanismProcessor(Set<AuthCallback> authCallbacks) {
		super();
		for (AuthCallback authCallback : authCallbacks) {
			this.authCallbacks.put(authCallback.support(), authCallback);
		}
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Auth auth = protocol.cast(Auth.class);
		AuthCallback callback = this.authCallbacks.get(auth.getMechanism());
		return callback != null ? !callback.auth(auth, context) : !context.write(Failure.INSTANCE_INVALIDMECHANISM).write(Stream.closeGraceFully()).close();
	}
}
