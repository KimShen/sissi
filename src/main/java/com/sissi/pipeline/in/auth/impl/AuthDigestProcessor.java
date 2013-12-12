package com.sissi.pipeline.in.auth.impl;

import javax.security.sasl.SaslException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.pipeline.in.auth.SaslServers;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.auth.Response;
import com.sissi.protocol.iq.auth.Success;

/**
 * @author kim 2013年11月26日
 */
public class AuthDigestProcessor extends ProxyProcessor {

	private final Log log = LogFactory.getLog(this.getClass());

	private final SaslServers saslServers;

	public AuthDigestProcessor(SaslServers saslServers) {
		super();
		this.saslServers = saslServers;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		try {
			return !this.isSuccess(context, protocol);
		} catch (Exception e) {
			this.log.debug(e);
			return true;
		}
	}

	private Boolean isSuccess(JIDContext context, Protocol protocol) throws SaslException {
		this.saslServers.get(context).evaluateResponse(Response.class.cast(protocol).getResponse());
		context.setAuth(true).write(Success.INSTANCE);
		return true;
	}
}
