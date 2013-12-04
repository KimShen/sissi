package com.sissi.pipeline.in.auth.impl;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.pipeline.in.auth.AuthCallback;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2013-10-24
 */
public class AuthForkProcessor extends UtilProcessor {

	private final List<AuthCallback> authCallbacks;

	public AuthForkProcessor(List<AuthCallback> authCallbacks) {
		super();
		this.authCallbacks = authCallbacks;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Auth auth = Auth.class.cast(protocol);
		for (AuthCallback ap : this.authCallbacks) {
			if (ap.isSupport(auth.getMechanism())) {
				return !ap.auth(context, auth);
			}
		}
		return true;
	}
}
