package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年2月18日
 */
public class PresenceOfflineMucProcessor extends ProxyProcessor {

	private Input input;

	public PresenceOfflineMucProcessor(Input input) {
		super();
		this.input = input;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence().clauses(Presence.class.cast(protocol).clauses()).setType(PresenceType.UNAVAILABLE);
		for (JID group : super.iSubscribedWho(context.jid())) {
			this.input.input(context, presence.setTo(group));
		}
		return true;
	}
}
