package com.sissi.pipeline.in.presence.status;

import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.SignatureContext;

/**
 * @author kim 2014年1月21日
 */
public class PresenceInit4FansProcessor extends ProxyProcessor {

	private final SignatureContext signatureContext;

	public PresenceInit4FansProcessor() {
		super();
		this.signatureContext = null;
	}

	public PresenceInit4FansProcessor(SignatureContext signatureContext) {
		super();
		this.signatureContext = signatureContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isPresence() ? true : this.init4Fans(context);
	}

	private Boolean init4Fans(JIDContext context) {
		for (String relation : super.iSubscribedWho(context.getJid().getBare())) {
			// Get presence from all resources of jid who subscribed me
			JID from = super.build(relation);
			Collection<String> resoures = super.resources(from);
			if (resoures.isEmpty() && this.signatureContext != null) {
				this.writeOfflinePresence(context, from);
			} else {
				this.writeOnlinePresence(context, from, resoures);
			}
		}
		return true;
	}

	private void writeOnlinePresence(JIDContext context, JID from, Collection<String> resoures) {
		for (String resource : resoures) {
			context.write(new Presence(from.setResource(resource), super.findOne(from, true).getStatus().getClauses()));
		}
	}

	private void writeOfflinePresence(JIDContext context, JID from) {
		context.write(new Presence().setFrom(from).setStatus(this.signatureContext.signature(from)).setType(PresenceType.UNAVAILABLE));
	}
}
