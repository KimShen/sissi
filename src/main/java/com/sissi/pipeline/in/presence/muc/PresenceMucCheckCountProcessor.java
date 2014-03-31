package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ServiceUnavailable;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月6日
 */
public class PresenceMucCheckCountProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("503").setType(ProtocolType.WAIT).add(ServiceUnavailable.DETAIL);

	private final MucConfigBuilder mucConfigBuilder;

	private final short limit;

	public PresenceMucCheckCountProcessor(short limit, MucConfigBuilder mucConfigBuilder) {
		super();
		this.limit = limit;
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.mucConfigBuilder.build(super.build(protocol.getTo())).allowed(context.jid(), MucConfig.COUNT, this.limit) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
