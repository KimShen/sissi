package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-20
 */
public class PresenceStatus2SelfProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Presence presence = Presence.class.cast(protocol);
		context.getStatus().asType(presence.getTypeAsText()).asShow(presence.getShowAsText()).asStatus(presence.getStatusAsText());
		return true;
	}
}
