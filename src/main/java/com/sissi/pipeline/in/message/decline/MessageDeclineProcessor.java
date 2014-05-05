package com.sissi.pipeline.in.message.decline;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.Decline;

/**
 * Decline.To转发
 * 
 * @author kim 2014年3月10日
 */
public class MessageDeclineProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Decline decline = protocol.cast(Message.class).getMuc().getDecline();
		super.findOne(super.build(decline.getTo()), true).write(this.prepare(context, protocol.cast(Message.class), decline).reply());
		return true;
	}

	private Message prepare(JIDContext context, Message message, Decline decline) {
		decline.setFrom(context.jid().asString());
		return message.noneThread();
	}
}