package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2InviteCheckRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	private final MucConfigBuilder mucConfigBuilder;

	public MessageMuc2InviteCheckRelationProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.mucConfigBuilder.build(super.build(protocol.getTo())).allowed(context.jid(), MucConfig.INVITE, protocol.cast(Message.class).getX().getInvite().getTo()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
