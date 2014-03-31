package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.RegistrationRequired;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年2月22日
 */
public class PresenceMucCheckAffiliationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("407").setType(ProtocolType.AUTH).add(RegistrationRequired.DETAIL);

	private final MucConfigBuilder mucConfigBuilder;

	public PresenceMucCheckAffiliationProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.mucConfigBuilder.build(super.build(protocol.getTo())).allowed(context.jid(), MucConfig.AFFILIATION_CHECK, context.jid()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
