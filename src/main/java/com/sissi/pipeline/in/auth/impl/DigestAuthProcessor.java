package com.sissi.pipeline.in.auth.impl;

import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.pipeline.in.auth.SaslServers;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.auth.Response;
import com.sissi.protocol.iq.auth.Success;

/**
 * @author kim 2013年11月26日
 */
public class DigestAuthProcessor extends ProxyProcessor {

	private final Log log = LogFactory.getLog(this.getClass());

	private final SaslServers saslServers;

	public DigestAuthProcessor(SaslServers saslServers) {
		super();
		this.saslServers = saslServers;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		try {
			return !this.isSuccess(context, protocol);
		} catch (Exception e) {
			this.log.debug(e.toString());
			Trace.trace(this.log, e);
			return true;
		}
	}

	private boolean isSuccess(JIDContext context, Protocol protocol) throws SaslException {
		SaslServer sasl = null;
		try {
			return (sasl = this.saslServers.pull(context)) != null ? context.write(new Success(sasl.evaluateResponse(Response.class.cast(protocol).getResponse()))).auth(true).auth() : false;
		} finally {
			if (sasl != null) {
				sasl.dispose();
			}
		}
	}
}
