package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年3月3日
 */
public class MessageMuc2PersistentProcessor extends ProxyProcessor {

	private final PersistentElementBox persistentElementBox;

	private final boolean log;

	public MessageMuc2PersistentProcessor(boolean log, PersistentElementBox persistentElementBox) {
		super();
		this.log = log;
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (this.log) {
			JID group = super.build(protocol.getTo());
			this.persistentElementBox.push(protocol.parent().setFrom(group.resource(super.ourRelation(context.jid(), group).name())));
		}
		return true;
	}
}
