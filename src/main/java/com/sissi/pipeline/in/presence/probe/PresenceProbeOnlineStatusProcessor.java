package com.sissi.pipeline.in.presence.probe;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年1月27日
 */
public class PresenceProbeOnlineStatusProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		for (String resource : super.resources(super.build(protocol.getTo()))) {
			context.write(new Presence(to.setResource(resource), super.findOne(to, true).getStatus().getClauses()).setId(protocol.getId()));
		}
		return true;
	}
}
