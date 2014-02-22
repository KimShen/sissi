package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.MucGroupConfig;
import com.sissi.ucenter.MucGroupContext;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfMessageProcessor extends ProxyProcessor {

	private final XUser x = new XUser().add("100");

	private final MucGroupContext mucGroupContext;

	private final String message;

	public PresenceMucJoin2SelfMessageProcessor(MucGroupContext mucGroupContext, String message) {
		super();
		this.mucGroupContext = mucGroupContext;
		this.message = message;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		return this.mucGroupContext.find(group).allowed(MucGroupConfig.HIDDEN_PURE, null) ? true : this.writeAndReturn(context, group);
	}

	private boolean writeAndReturn(JIDContext context, JID group) {
		context.write(new Message().setBody(new Body(this.message)).setX(this.x).setType(MessageType.GROUPCHAT).setFrom(group.asStringWithBare()));
		return true;
	}
}
