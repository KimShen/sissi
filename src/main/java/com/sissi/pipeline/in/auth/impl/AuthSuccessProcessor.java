package com.sissi.pipeline.in.auth.impl;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.pipeline.in.auth.AuthAccessor;
import com.sissi.pipeline.in.auth.AuthCertificate;
import com.sissi.pipeline.in.auth.AuthNormalization;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.login.Auth;
import com.sissi.protocol.iq.login.Success;

/**
 * @author kim 2013-10-24
 */
public class AuthSuccessProcessor extends UtilProcessor {

	private List<AuthNormalization> authNormalizations;

	private AuthAccessor authAccessor;

	public AuthSuccessProcessor(List<AuthNormalization> authNormalizations, AuthAccessor authAccessor) {
		super();
		this.authNormalizations = authNormalizations;
		this.authAccessor = authAccessor;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Auth auth = Auth.class.cast(protocol);
		for (AuthNormalization un : this.authNormalizations) {
			if (un.isSupport(auth.getMechanism())) {
				AuthCertificate certificate = un.normalize(auth);
				if (context.setAuth(this.authAccessor.access(certificate)).isAuth()) {
					return this.writeSuccessProtocol(context, certificate);
				}
			}
		}
		return true;
	}

	private boolean writeSuccessProtocol(JIDContext context, AuthCertificate certificate) {
		context.setJid(super.jidBuilder.build(certificate.getUser(), null));
		context.write(Success.INSTANCE);
		return false;
	}
}
