package com.sissi.pipeline.in.auth.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.Sasl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.auth.AuthCallback;
import com.sissi.pipeline.in.auth.SaslServers;
import com.sissi.protocol.iq.auth.Auth;
import com.sissi.protocol.iq.auth.Challenge;
import com.sissi.ucenter.access.AuthAccessor;

/**
 * @author kim 2013年11月25日
 */
public class DigestAuthCallback implements AuthCallback {

	private final static String mechanism = "DIGEST-MD5";

	private final String protocol = "XMPP";

	@SuppressWarnings("serial")
	private final Map<Class<? extends Callback>, Handler> handlers = new HashMap<Class<? extends Callback>, Handler>() {
		{
			put(NameCallback.class, new NameCallbackHandler());
			put(PasswordCallback.class, new PasswordCallbackHandler());
			put(AuthorizeCallback.class, new AuthorizeCallbackHandler());
		}
	};

	@SuppressWarnings("serial")
	private final Map<String, String> props = new TreeMap<String, String>() {
		{
			put(Sasl.QOP, "auth");
		}
	};

	private final Log log = LogFactory.getLog(this.getClass());

	private final JIDBuilder jidBuilder;

	private final SaslServers saslServers;

	private final AuthAccessor authAccessor;

	public DigestAuthCallback(JIDBuilder jidBuilder, SaslServers saslServers, AuthAccessor authAccessor) {
		super();
		this.jidBuilder = jidBuilder;
		this.saslServers = saslServers;
		this.authAccessor = authAccessor;
	}

	@Override
	public boolean auth(Auth auth, JIDContext context) {
		try {
			context.write(new Challenge(this.saslServers.push(context, Sasl.createSaslServer(mechanism, this.protocol, context.domain(), this.props, new ServerCallbackHandler(context))).evaluateResponse(new byte[0])));
			return true;
		} catch (Exception e) {
			this.log.error(e.toString());
			Trace.trace(this.log, e);
			return false;
		}
	}

	public String support() {
		return mechanism;
	}

	private class ServerCallbackHandler implements CallbackHandler {

		private final JIDContext context;

		private ServerCallbackHandler(JIDContext context) {
			super();
			this.context = context;
		}

		public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
			for (Callback callback : callbacks) {
				Handler handler = DigestAuthCallback.this.handlers.get(callback.getClass());
				if (handler != null) {
					handler.handler(this.context, callback);
				}
			}
		}
	}

	private interface Handler {

		public void handler(JIDContext context, Callback callback);
	}

	private class NameCallbackHandler implements Handler {

		@Override
		public void handler(JIDContext context, Callback callback) {
			context.jid(DigestAuthCallback.this.jidBuilder.build(NameCallback.class.cast(callback).getDefaultName(), null));
		}
	}

	private class PasswordCallbackHandler implements Handler {

		@Override
		public void handler(JIDContext context, Callback callback) {
			String pass = DigestAuthCallback.this.authAccessor.access(context.jid().user(), null);
			PasswordCallback.class.cast(callback).setPassword(pass != null ? pass.toCharArray() : new char[0]);
		}
	}

	private class AuthorizeCallbackHandler implements Handler {

		@Override
		public void handler(JIDContext context, Callback callback) {
			AuthorizeCallback.class.cast(callback).setAuthorized(true);
		}
	}
}
