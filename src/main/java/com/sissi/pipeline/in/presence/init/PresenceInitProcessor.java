package com.sissi.pipeline.in.presence.init;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * 出席状态调整(内部状态)
 * 
 * @author kim 2014年1月21日
 */
public class PresenceInitProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.online();
		return true;
	}
}
