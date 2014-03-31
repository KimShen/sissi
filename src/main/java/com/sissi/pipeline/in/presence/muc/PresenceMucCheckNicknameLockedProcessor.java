package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAcceptable;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月7日
 */
public class PresenceMucCheckNicknameLockedProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("409").setType(ProtocolType.CANCEL).add(NotAcceptable.DETAIL);

	private final MucConfigBuilder mucConfigBuilder;

	public PresenceMucCheckNicknameLockedProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		return this.mucConfigBuilder.build(group).allowed(context.jid(), MucConfig.NICK, group.resource()) ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
