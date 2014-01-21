package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年1月21日
 */
public class PresenceInit4SelfsProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isPresence() ? true : this.init4Selfs(context);
	}

	private Boolean init4Selfs(JIDContext context) {
		JID other = super.build(context.getJid().asString());
		for (String resource : super.resources(context.getJid())) {
			context.write(new Presence().setFrom(other.setResource(resource)));
		}
		return true;
	}
}
