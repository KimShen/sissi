package com.sissi.pipeline.in.auth.impl;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.auth.AuthCallback;
import com.sissi.protocol.iq.auth.Auth;
import com.sissi.protocol.iq.auth.Success;
import com.sissi.ucenter.AuthAccessor;

/**
 * @author kim 2013-10-24
 */
public class PlainAuthCallback implements AuthCallback {

	public final static String MECHANISM = "PLAIN";

	private final Base64 base64 = new Base64();

	private final Log log = LogFactory.getLog(this.getClass());

	private final JIDBuilder jidBuilder;

	private final AuthAccessor authAccessor;

	public PlainAuthCallback(JIDBuilder jidBuilder, AuthAccessor authAccessor) {
		super();
		this.jidBuilder = jidBuilder;
		this.authAccessor = authAccessor;
	}

	@Override
	public Boolean auth(Auth auth, JIDContext context) {
		AuthCertificate certificate = new AuthCertificate(auth);
		return context.setAuth(certificate.getPass().equals(this.authAccessor.access(certificate.getUser()))).isAuth() ? this.writeSuccessProtocol(context, certificate) : true;
	}

	@Override
	public Boolean isSupport(String mechanism) {
		return MECHANISM.equals(mechanism);
	}

	private boolean writeSuccessProtocol(JIDContext context, AuthCertificate certificate) {
		context.setJid(this.jidBuilder.build(certificate.getUser(), null)).write(Success.INSTANCE);
		return true;
	}

	private class AuthCertificate {

		private final String user;

		private final String pass;

		public AuthCertificate(Auth auth) {
			super();
			byte[] afterDecode = base64.decode(auth.getText());
			if (PlainAuthCallback.this.log.isDebugEnabled()) {
				PlainAuthCallback.this.log.debug("User/Pass is: " + Arrays.toString(afterDecode));
			}
			int passStart = ArrayUtils.lastIndexOf(afterDecode, (byte) 0) + 1;
			this.pass = new String(afterDecode, passStart, afterDecode.length - passStart).trim();
			this.user = new String(afterDecode, 0, passStart).trim();
		}

		public String getUser() {
			return this.user;
		}

		public String getPass() {
			return this.pass;
		}
	}
}
