package com.sissi.server.exchange.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.persistent.Persistent;
import com.sissi.server.exchange.Recall;

/**
 * 重发
 * 
 * @author kim 2014年2月27日
 */
public class ResendCall implements Recall {

	private final Addressing addressing;

	private final JIDBuilder jidBuilder;

	private final Persistent persistent;

	public ResendCall(Addressing addressing, JIDBuilder jidBuilder, Persistent persistent) {
		super();
		this.addressing = addressing;
		this.jidBuilder = jidBuilder;
		this.persistent = persistent;
	}

	@Override
	public Recall call(String to) {
		JID jid = this.jidBuilder.build(to);
		JIDContext context = this.addressing.find(jid);
		if (context.binding()) {
			context.write(this.persistent.pull(jid));
		}
		return this;
	}
}
