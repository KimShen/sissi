package com.sissi.pipeline.in.auth.impl;

import javax.security.sasl.SaslException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.pipeline.in.auth.SaslServers;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.auth.Response;
import com.sissi.protocol.iq.auth.Success;

/**
 * @author kim 2013年11月26日
 */
public class AuthDigestProcessor extends UtilProcessor {

	private final Log log = LogFactory.getLog(this.getClass());

	private final SaslServers saslServers;

	public AuthDigestProcessor(SaslServers saslServers) {
		super();
		this.saslServers = saslServers;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		try {
			return this.isSuccess(context, protocol);
		} catch (Exception e) {
			this.log.debug(e);
			return true;
		}
	}

	private Boolean isSuccess(JIDContext context, Protocol protocol) throws SaslException {
		Response res = Response.class.cast(protocol);
		this.saslServers.get(context).evaluateResponse(res.getResponse());
		context.setAuth(true).write(Success.INSTANCE);
		return false;
	}
}
