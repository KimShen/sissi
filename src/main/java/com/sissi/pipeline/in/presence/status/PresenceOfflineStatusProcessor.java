package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * Presence unavailable, JIDContext离线
 * 
 * @author kim 2014年1月27日
 */
public class PresenceOfflineStatusProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.offline();
		return true;
	}
}
