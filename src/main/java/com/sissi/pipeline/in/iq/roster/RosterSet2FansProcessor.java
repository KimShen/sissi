package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-10-31
 */
public class RosterSet2FansProcessor extends ProxyProcessor {

	private final Input proxy;

	public RosterSet2FansProcessor(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.proxy.input(context, new Presence().setType(PresenceType.SUBSCRIBE).setTo(protocol.cast(Roster.class).getFirstItem().getJid()));
		return true;
	}
}
