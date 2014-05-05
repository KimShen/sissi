package com.sissi.pipeline.in.message.muc.history;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.offline.History;
import com.sissi.protocol.presence.Presence;

/**
 * 历史消息
 * 
 * @author kim 2014年4月2日
 */
public class MessageMucHistoryProcessor extends ProxyProcessor {

	private final Input proxy;

	public MessageMucHistoryProcessor(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = Presence.muc();
		presence.findField(XMuc.NAME, XMuc.class).set(History.NAME, protocol.cast(Message.class).getHistory());
		return this.proxy.input(context, presence.setTo(protocol.getTo()));
	}
}
