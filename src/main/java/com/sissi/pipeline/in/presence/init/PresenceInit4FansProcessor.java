package com.sissi.pipeline.in.presence.init;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 当前JID已订阅者(From)所有资源出席通知
 * 
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
		return this.init4Fans(protocol.cast(Presence.class).clone(), context);
	}

	/**
	 * 所有已订阅者所有资源
	 * 
	 * @param presence
	 * @param context
	 * @return
	 */
	private boolean init4Fans(Presence presence, JIDContext context) {
		for (JID jid : super.iSubscribedWho(context.jid())) {
			JIDs resoures = super.resources(jid);
			if (resoures.isEmpty()) {
				this.writeOffline(context, jid);
			} else {
				this.writeOnlinePresence(context, jid, resoures);
			}
		}
		return true;
	}

	/**
	 * 离线状态(含签名)
	 * 
	 * @param context
	 * @param from
	 */
	private void writeOffline(JIDContext context, JID from) {
		context.write(new Presence().setFrom(from).status(this.vCardContext.pull(from, VCardContext.FIELD_SIGNATURE).getValue()).type(PresenceType.UNAVAILABLE), true);
	}

	/**
	 * 在线状态
	 * 
	 * @param context
	 * @param from
	 * @param resoures
	 */
	private void writeOnlinePresence(JIDContext context, JID from, JIDs resoures) {
		for (JID resource : resoures) {
			context.write(new Presence().setFrom(resource).clauses(super.findOne(resource, true).status().clauses()), true);
		}
	}
}
