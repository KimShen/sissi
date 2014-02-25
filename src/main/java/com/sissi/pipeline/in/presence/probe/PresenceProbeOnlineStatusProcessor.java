package com.sissi.pipeline.in.presence.probe;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月27日
 */
public class PresenceProbeOnlineStatusProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = protocol.cast(Presence.class).clear().setType(PresenceType.PROBE);
		for (JID resource : super.resources(super.build(protocol.getTo()))) {
			context.write(presence.setFrom(resource).clauses(super.findOne(resource, true).status().clauses()));
		}
		return true;
	}
}
