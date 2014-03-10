package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2ThreadProcessor extends ProxyProcessor {

	private final boolean trace;

	public MessageMuc2ThreadProcessor(boolean trace) {
		super();
		this.trace = trace;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (!this.trace) {
			protocol.cast(Message.class).noneThread();
		}
		return true;
	}
}
