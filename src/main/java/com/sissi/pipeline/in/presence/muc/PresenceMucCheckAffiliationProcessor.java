package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.RegistrationRequired;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年2月22日
 */
public class PresenceMucCheckAffiliationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(RegistrationRequired.DETAIL);

	private final MucConfigBuilder mucGroupContext;

	public PresenceMucCheckAffiliationProcessor(MucConfigBuilder mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return false;
		// return this.mucGroupContext.build(super.build(protocol.getTo())).allowed(MucConfig.AFFILIATIONS, context.jid()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
