package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-10-29
 */
public class Session4SelfsProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID other = super.build(context.getJid().asString());
		for(String resource : super.resources(context.getJid())){
			context.write(new Presence().setFrom(other.setResource(resource)));
		}
		return true;
	}
}
