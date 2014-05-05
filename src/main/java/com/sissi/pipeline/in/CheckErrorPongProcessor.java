package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * 如果Protocol.type = error则尝试context.pong
 * 
 * @author kim 2014年1月29日
 */
public class CheckErrorPongProcessor implements Input {

	/*
	 * Always return true
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.type(ProtocolType.ERROR) ? this.pong(context, protocol) : true;
	}

	private boolean pong(JIDContext context, Protocol protocol) {
		context.pong(protocol.parent());
		return true;
	}
}
