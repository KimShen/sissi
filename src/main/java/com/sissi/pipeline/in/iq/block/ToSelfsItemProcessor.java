package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * 广播Block(To: 向当前JID所有资源)
 * 
 * @author kim 2013年12月12日
 */
public class ToSelfsItemProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.jid(), protocol.parent());
		return true;
	}
}
