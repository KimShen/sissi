package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年3月3日
 */
public class MessagePersistentProcessor extends ProxyProcessor {

	private final PersistentElementBox persistentElementBox;

	public MessagePersistentProcessor(PersistentElementBox persistentElementBox) {
		super();
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.persistentElementBox.push(protocol.parent());
		return true;
	}

}
