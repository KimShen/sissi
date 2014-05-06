package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * MUC房间状态更新. 已上线(Presence)才更新
 * 
 * @author kim 2014年2月18日
 */
public class PresenceOnlineMucProcessor extends ProxyProcessor {

	private final Input proxy;

	public PresenceOnlineMucProcessor(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (context.onlined()) {
			Presence presence = protocol.cast(Presence.class).clone();
			for (JID group : super.iSubscribedWho(context.jid())) {
				this.proxy.input(context, presence.setTo(group));
			}
		}
		return true;
	}
}
