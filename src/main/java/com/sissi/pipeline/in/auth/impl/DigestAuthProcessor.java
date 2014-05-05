package com.sissi.pipeline.in.auth.impl;

import javax.security.sasl.SaslServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
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

	private final Input proxy;

	public DigestAuthProcessor(SaslServers saslServers, Input proxy) {
		super();
		this.saslServers = saslServers;
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		try {
			SaslServer sasl = null;
			try {
				return (sasl = this.saslServers.pull(context)) != null ? context.write(new Success(sasl.evaluateResponse(protocol.cast(Response.class).getResponse()))).auth(true).auth() : false;
			} finally {
				if (sasl != null) {
					sasl.dispose();
				}
			}
		} catch (Exception e) {
			this.log.debug(e.toString());
			Trace.trace(this.log, e);
			return this.proxy.input(context, protocol);
		}
	}
}
