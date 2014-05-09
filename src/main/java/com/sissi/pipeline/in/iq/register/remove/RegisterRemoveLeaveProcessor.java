package com.sissi.pipeline.in.iq.register.remove;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAuthorized;

/**
 * JID所有资源离线
 * 
 * @author kim 2014年5月9日
 */
public class RegisterRemoveLeaveProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.find(context.jid()).write(Stream.closeWhenRunning(new ServerError().add(NotAuthorized.DETAIL_ELEMENT))).close();
	}
}