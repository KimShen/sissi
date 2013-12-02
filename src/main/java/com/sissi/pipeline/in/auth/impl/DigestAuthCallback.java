package com.sissi.pipeline.in.auth.impl;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.auth.AuthAccessor;
import com.sissi.pipeline.in.auth.AuthCallback;
import com.sissi.pipeline.in.auth.SaslServerPool;
import com.sissi.protocol.iq.login.Auth;
import com.sissi.protocol.iq.login.Challenge;

/**
 * @author kim 2013年11月25日
 */
public class DigestAuthCallback implements AuthCallback {

	public final static String MECHANISM = "DIGEST-MD5";

	public final static String PROTOCOL = "XMPP";

	@SuppressWarnings("serial")
	private final static Map<String, String> PROPS = new TreeMap<String, String>() {
		{
			put(Sasl.QOP, "auth");
		}
	};

	private final Log log = LogFactory.getLog(this.getClass());

	private String host;

	private JIDBuilder jidBuilder;

	private AuthAccessor authAccessor;

	private SaslServerPool saslServerPool;

	public DigestAuthCallback(String host, JIDBuilder jidBuilder, AuthAccessor authAccessor, SaslServerPool saslServerPool) {
		super();
		this.host = host;
		this.jidBuilder = jidBuilder;
		this.authAccessor = authAccessor;
		this.saslServerPool = saslServerPool;
	}

	@Override
	public Boolean auth(JIDContext context, Auth auth) {
		try {
			SaslServer sasl = Sasl.createSaslServer(MECHANISM, PROTOCOL, this.host, PROPS, new ServerCallbackHandler(context));
			this.saslServerPool.offer(context, sasl);
			context.write(new Challenge(sasl.evaluateResponse(new byte[0])));
			return true;
		} catch (Exception e) {
			this.log.fatal(e);
			return false;
		}
	}

	@Override
	public Boolean isSupport(String mechanism) {
		return MECHANISM.equals(mechanism);
	}

	public class ServerCallbackHandler implements CallbackHandler {

		private JIDContext context;

		public ServerCallbackHandler(JIDContext context) {
			super();
			this.context = context;
		}

		public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
			for (Callback callback : callbacks) {
				String className = callback.getClass().getSimpleName();
				if (className.equals("NameCallback")) {
					this.context.setJid(DigestAuthCallback.this.jidBuilder.build(NameCallback.class.cast(callback).getDefaultName(), null));
				} else if (className.equals("PasswordCallback")) {
					String password = DigestAuthCallback.this.authAccessor.access(this.context.getJid().getUser());
					((PasswordCallback) callback).setPassword(password != null ? password.toCharArray() : new char[0]);
				} else if (className.equals("AuthorizeCallback")) {
					AuthorizeCallback authCallback = ((AuthorizeCallback) callback);
					authCallback.setAuthorized(true);
				}
			}
		}
	}
}
