package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年3月3日
 */
public class MessagePersistentProcessor extends ProxyProcessor {

	private final PersistentElementBox persistentElementBox;

	private final boolean log;

	public MessagePersistentProcessor(boolean log, PersistentElementBox persistentElementBox) {
		super();
		this.log = log;
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (this.log) {
			this.persistentElementBox.push(protocol.parent().setFrom(super.build(protocol.getTo()).resource(super.ourRelation(context.jid(), super.build(protocol.getTo())).getName())));
		}
		return true;
	}
}
