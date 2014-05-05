package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * Presence unavailable, JIDContext关闭
 * 
 * @author kim 2014年1月27日
 */
public class PresenceOfflineCloseProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !context.close();
	}
}
