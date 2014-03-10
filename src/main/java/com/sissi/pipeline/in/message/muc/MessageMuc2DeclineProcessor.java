package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XDecline;

/**
 * @author kim 2014年3月10日
 */
public class MessageMuc2DeclineProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XDecline decline = protocol.cast(Message.class).getX().getDecline();
		super.findOne(super.build(decline.getTo()), true).write(this.prepareAndReturn(context, protocol, decline).reply());
		return true;
	}

	private Protocol prepareAndReturn(JIDContext context, Protocol protocol, XDecline decline) {
		decline.setFrom(context.jid().asString());
		return protocol;
	}
}