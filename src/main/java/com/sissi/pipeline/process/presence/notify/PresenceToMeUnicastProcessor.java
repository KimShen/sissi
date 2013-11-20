package com.sissi.pipeline.process.presence.notify;

import com.sissi.context.JIDContext;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-15
 */
public class PresenceToMeUnicastProcessor extends PresenceUnicastProcessor {

	@Override
	public Presence prepare(JIDContext context, Presence presence) {
		presence.setTo(context.jid().asStringWithBare());
		return presence;
	}

}
