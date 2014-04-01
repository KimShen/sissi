package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年2月18日
 */
public class PresenceMucJoinProcessor extends ProxyProcessor {

	private final Input proxy;

	private final MucConfigBuilder mucConfigBuilder;

	public PresenceMucJoinProcessor(Input proxy, MucConfigBuilder mucConfigBuilder) {
		super();
		this.proxy = proxy;
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (!context.presence()) {
			Presence presence = Presence.muc();
			for (JID jid : super.iSubscribedWho(context.jid())) {
				if (this.mucConfigBuilder.build(jid).allowed(jid, MucConfig.PERSISTENT, null)) {
					this.proxy.input(context, presence.setTo(jid));
				}
			}
		}
		return true;
	}
}
