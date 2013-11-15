package com.sissi.pipeline.process.auth.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.pipeline.process.auth.AuthCertificate;
import com.sissi.pipeline.process.auth.AuthNormalization;
import com.sissi.protocol.auth.Auth;

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

		private final static Log LOG = LogFactory.getLog(PlainUser.class);

		private String user;

		private String pass;

		public PlainUser(Auth auth) {
			super();
			byte[] afterDecode = base64.decode(auth.getText());
			int passStart = ArrayUtils.lastIndexOf(afterDecode, (byte) 0) + 1;
			this.pass = new String(afterDecode, passStart, afterDecode.length - passStart).trim();
			this.user = new String(afterDecode, 0, passStart).trim();
			LOG.debug("PlainUser: " + this.user + " / " + this.pass);
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
