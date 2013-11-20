package com.sissi.pipeline.in.presence.state;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-20
 */
public class PresenceStateLocationProcessor extends UtilProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = Presence.class.cast(protocol);
		context.getPresence().type(presence.getType());
		context.getPresence().show(presence.getShowText());
		context.getPresence().status(presence.getStatusText());
		return true;
	}
}
