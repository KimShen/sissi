package com.sissi.server.getout.impl;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.server.getout.Getout;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 强制离席
 * 
 * @author kim 2013-11-20
 */
public class PresenceGetout implements Getout {

	private final Input proxy;

	private final VCardContext vCardContext;

	public PresenceGetout(Input proxy, VCardContext vCardContext) {
		super();
		this.proxy = proxy;
		this.vCardContext = vCardContext;
	}

	@Override
	public PresenceGetout getout(JIDContext context) {
		this.proxy.input(context, new Presence().setFrom(context.jid()).status(this.vCardContext.pull(context.jid(), VCardContext.FIELD_SIGNATURE).getValue()).type(PresenceType.UNAVAILABLE));
		return this;
	}
}
