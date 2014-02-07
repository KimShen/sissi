package com.sissi.pipeline.in.presence.probe;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月27日
 */
public class PresenceProbeOfflineStatusProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.resources(super.build(protocol.getTo())).isEmpty() ? this.writeAndReturn(context, Presence.class.cast(protocol)) : true;
	}

	private Boolean writeAndReturn(JIDContext context, Presence presence) {
		context.write(presence.clear().reply().setType(PresenceType.UNAVAILABLE.toString()));
		return false;
	}
}
