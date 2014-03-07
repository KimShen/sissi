package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfMessageWarningProcessor extends ProxyProcessor {

	private final XUser x = new XUser().add("100");

	private final MucConfigBuilder mucConfigBuilder;

	private final Body body;

	public PresenceMucJoin2SelfMessageWarningProcessor(MucConfigBuilder mucConfigBuilder, String message) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
		this.body = new Body(message);
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		return this.mucConfigBuilder.build(group).allowed(context.jid(), MucConfig.HIDDEN_NATIVE, null) ? true : this.writeAndReturn(context, group);
	}

	private boolean writeAndReturn(JIDContext context, JID group) {
		context.write(new Message().setBody(this.body).setX(this.x).setType(MessageType.GROUPCHAT).setFrom(group.asStringWithBare()));
		return true;
	}
}
