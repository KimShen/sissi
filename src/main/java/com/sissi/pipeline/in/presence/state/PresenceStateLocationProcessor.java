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
	public Boolean input(JIDContext context, Protocol protocol) {
		Presence presence = Presence.class.cast(protocol);
		context.getPresence().setTypeText(presence.getTypeText()).setShowText(presence.getShowText()).setStatusText(presence.getStatusText());
		return true;
	}
}
