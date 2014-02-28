package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.user.VCardContext;

/**
 * @author kim 2014年1月21日
 */
public class PresenceInit4FansProcessor extends ProxyProcessor {

	private final VCardContext vCardContext;

	public PresenceInit4FansProcessor(VCardContext vCardContext) {
		super();
		this.vCardContext = vCardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.presence() ? true : this.init4Fans(context);
	}

	private boolean init4Fans(JIDContext context) {
		for (JID jid : super.iSubscribedWho(context.jid())) {
			JIDs resoures = super.resources(jid);
			if (resoures.isEmpty()) {
				this.writeOfflinePresence(context, jid);
			} else {
				this.writeOnlinePresence(context, jid, resoures);
			}
		}
		return true;
	}

	private void writeOfflinePresence(JIDContext context, JID from) {
		context.write(new Presence().setFrom(from).status(this.vCardContext.get(from, VCardContext.FIELD_SIGNATURE).getValue()).setType(PresenceType.UNAVAILABLE), true);
	}

	private void writeOnlinePresence(JIDContext context, JID from, JIDs resoures) {
		Presence presence = new Presence();
		for (JID resource : resoures) {
			context.write(presence.setFrom(resource).clauses(super.findOne(resource, true).status().clauses()), true);
		}
	}
}
