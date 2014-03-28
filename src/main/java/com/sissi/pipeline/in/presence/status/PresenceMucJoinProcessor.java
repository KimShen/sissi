package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年2月18日
 */
public class PresenceMucJoinProcessor extends ProxyProcessor {

	private final Input proxy;

	public PresenceMucJoinProcessor(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (!context.presence()) {
			Presence presence = Presence.muc();
			for (JID jid : super.iSubscribedWho(context.jid())) {
				this.proxy.input(context, presence.setTo(jid));
			}
		}
		return true;
	}
}
