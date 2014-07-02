package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.persistent.Persistent;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * 消息重复校验
 * 
 * @author kim 2014年7月2日
 */
public class MessageCheckExistsProcessor implements Input {

	private final Persistent persistent;

	public MessageCheckExistsProcessor(Persistent persistent) {
		super();
		this.persistent = persistent;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !this.persistent.exists(protocol.parent().getId());
	}
}