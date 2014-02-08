package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.VCardContext;

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
		return context.presented() ? true : this.init4Fans(context);
	}

	private Boolean init4Fans(JIDContext context) {
		for (JID jid : super.iSubscribedWho(context.jid())) {
			JIDs resoures = super.resources(jid);
			if (resoures.isEmpty() && this.vCardContext != null) {
				this.writeOfflinePresence(context, jid);
			} else {
				this.writeOnlinePresence(context, jid, resoures);
			}
		}
		return true;
	}

	private void writeOfflinePresence(JIDContext context, JID from) {
		context.write(new Presence().setFrom(from).setStatus(this.vCardContext.get(from, VCardContext.SIGNATURE).getValue()).setType(PresenceType.UNAVAILABLE));
	}

	private void writeOnlinePresence(JIDContext context, JID from, JIDs resoures) {
		Presence presence = new Presence();
		for (JID resource : resoures) {
			context.write(presence.clear().setFrom(resource).clauses(super.findOne(resource, true).status().clauses()));
		}
	}
}
