package com.sissi.pipeline.in.iq.last;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.last.Last;
import com.sissi.server.status.ServerStatus;

/**
 * 服务器域 IDLE
 * 
 * @author kim 2014年2月10日
 */
public class LastServerProcessor extends ProxyProcessor {

	private final ServerStatus status;

	public LastServerProcessor(ServerStatus status) {
		super();
		this.status = status;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Last.class).seconds().seconds(this.status.peek(ServerStatus.STATUS_STARTED, String.class)).parent().reply().reply().setType(ProtocolType.RESULT));
		return false;
	}
}
