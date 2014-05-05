package com.sissi.pipeline.in.presence.probe;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * 探针请求反馈(所有资源)
 * 
 * @author kim 2014年1月27日
 */
public class PresenceProbeOnlineProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = protocol.cast(Presence.class);
		for (JID resource : super.resources(super.build(protocol.getTo()))) {
			super.findOne(resource, true).write(presence.setFrom(context.jid()).clauses(super.findOne(resource, true).status().clauses()).type(PresenceType.PROBE));
		}
		return true;
	}
}
