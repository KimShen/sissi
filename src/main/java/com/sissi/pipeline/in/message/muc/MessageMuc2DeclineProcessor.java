package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.Decline;

/**
 * @author kim 2014年3月10日
 */
public class MessageMuc2DeclineProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Decline decline = protocol.cast(Message.class).getUser().getDecline();
		super.findOne(super.build(decline.getTo()), true).write(this.prepareAndReturn(context, protocol.cast(Message.class), decline).reply());
		return true;
	}

	private Message prepareAndReturn(JIDContext context, Message message, Decline decline) {
		decline.setFrom(context.jid().asString());
		return message.noneThread();
	}
}