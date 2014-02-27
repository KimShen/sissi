package com.sissi.server.exchange.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.server.exchange.DelegationCallback;

/**
 * @author kim 2014年2月27日
 */
public class ResendDelegationCallback implements DelegationCallback {

	private final Addressing addressing;

	private final JIDBuilder jidBuilder;

	private final PersistentElementBox persistentElementBox;

	public ResendDelegationCallback(Addressing addressing, JIDBuilder jidBuilder, PersistentElementBox persistentElementBox) {
		super();
		this.addressing = addressing;
		this.jidBuilder = jidBuilder;
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public DelegationCallback callback(String to) {
		JID jid = this.jidBuilder.build(to);
		if (!this.addressing.resources(jid).isEmpty()) {
			this.addressing.find(jid).write(this.persistentElementBox.pull(jid));
		}
		return this;
	}
}
