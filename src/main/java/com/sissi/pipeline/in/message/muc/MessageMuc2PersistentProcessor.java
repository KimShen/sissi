package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.persistent.Persistent;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年3月3日
 */
public class MessageMuc2PersistentProcessor extends ProxyProcessor {

	private final Persistent persistent;

	public MessageMuc2PersistentProcessor(Persistent persistentElementBox) {
		super();
		this.persistent = persistentElementBox;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		this.persistent.push(protocol.parent().setFrom(group.resource(super.ourRelation(context.jid(), group).name())));
		return true;
	}
}
