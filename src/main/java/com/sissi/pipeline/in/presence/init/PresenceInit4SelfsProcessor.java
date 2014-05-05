package com.sissi.pipeline.in.presence.init;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * 当前JID被订阅者(To)所有资源出席通知
 * 
 * @author kim 2014年1月21日
 */
public class PresenceInit4SelfsProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.writePresence(protocol.cast(Presence.class).clone(), context);
	}

	private boolean writePresence(Presence presence, JIDContext context) {
		for (JID resource : super.resources(context.jid())) {
			context.write(new Presence().setFrom(resource).clauses(super.findOne(resource, true).status().clauses()), true);
		}
		return true;
	}
}
