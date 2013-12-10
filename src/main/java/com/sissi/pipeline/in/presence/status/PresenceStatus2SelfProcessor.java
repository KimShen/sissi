package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-20
 */
public class PresenceStatus2SelfProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Presence presence = Presence.class.cast(protocol);
		context.getOnlineStatus().asType(presence.getTypeAsText()).asShow(presence.getShowAsText()).asStatus(presence.getStatusAsText());
		return true;
	}
}
