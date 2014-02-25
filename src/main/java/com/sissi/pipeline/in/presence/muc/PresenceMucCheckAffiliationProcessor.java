package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.RegistrationRequired;
import com.sissi.ucenter.MucGroupConfig;
import com.sissi.ucenter.MucGroupContext;

/**
 * @author kim 2014年2月22日
 */
public class PresenceMucCheckAffiliationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(RegistrationRequired.DETAIL);

	private final MucGroupContext mucGroupContext;

	public PresenceMucCheckAffiliationProcessor(MucGroupContext mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.mucGroupContext.find(super.build(protocol.getTo())).allowed(MucGroupConfig.AFFILIATIONS, context.jid()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(this.error));
		return false;
	}
}
