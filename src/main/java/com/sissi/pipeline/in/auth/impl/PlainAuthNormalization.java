package com.sissi.pipeline.in.auth.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;

import com.sissi.pipeline.in.auth.AuthCertificate;
import com.sissi.pipeline.in.auth.AuthNormalization;
import com.sissi.protocol.iq.login.Auth;

/**
 * @author kim 2013-10-24
 */
public class PlainAuthNormalization implements AuthNormalization {

	public final static String MECHANISM = "PLAIN";

	private final static Base64 base64 = new Base64();

	@Override
	public AuthCertificate normalize(Auth auth) {
		return new PlainUser(auth);
	}

	@Override
	public Boolean isSupport(String mechanism) {
		return MECHANISM.equals(mechanism);
	}

	private static class PlainUser implements AuthCertificate {

		private String user;

		private String pass;

		public PlainUser(Auth auth) {
			super();
			byte[] afterDecode = base64.decode(auth.getText());
			int passStart = ArrayUtils.lastIndexOf(afterDecode, (byte) 0) + 1;
			this.pass = new String(afterDecode, passStart, afterDecode.length - passStart).trim();
			this.user = new String(afterDecode, 0, passStart).trim();
		}

		@Override
		public String getUser() {
			return this.user;
		}

		@Override
		public String getPass() {
			return this.pass;
		}
	}
}
