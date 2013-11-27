package com.sissi.pipeline.in.auth.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.pipeline.in.auth.SaslServerPool;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.login.Response;
import com.sissi.protocol.iq.login.Success;

/**
 * @author kim 2013年11月26日
 */
public class AuthDigestProcessor extends UtilProcessor {
	
	private final Log log = LogFactory.getLog(this.getClass());

	private SaslServerPool saslServerPool;

	public AuthDigestProcessor(SaslServerPool saslServerPool) {
		super();
		this.saslServerPool = saslServerPool;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Response res = Response.class.cast(protocol);
		try {
			this.saslServerPool.take(context).evaluateResponse(res.getResponse());
			context.setAuth(true);
			context.write(Success.INSTANCE);
			return false;
		} catch (Exception e) {
			this.log.debug(e);
		}
		return true;
	}
}
