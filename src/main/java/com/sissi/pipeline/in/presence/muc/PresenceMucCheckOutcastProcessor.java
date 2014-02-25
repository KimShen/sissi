package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年2月18日
 */
public class PresenceMucCheckOutcastProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		// Not outcast
		return super.ourRelation(context.jid(), super.build(protocol.getTo())).isActivate() ? true : false;
	}
}
