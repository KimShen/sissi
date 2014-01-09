package com.sissi.pipeline.in.iq.ping;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月8日
 */
public class PongProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.pong(protocol.getParent());
		return true;
	}
}
