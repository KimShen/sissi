package com.sissi.pipeline.in.iq.last;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.last.Last;
import com.sissi.server.ServerStatus;

/**
 * @author kim 2014年2月10日
 */
public class LastServerProcessor extends ProxyProcessor {

	private final ServerStatus serverStatus;

	public LastServerProcessor(ServerStatus serverStatus) {
		super();
		this.serverStatus = serverStatus;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Last.class).seconds().seconds(this.serverStatus.take(ServerStatus.STATUS_STARTED, String.class)).parent().reply().reply().setType(ProtocolType.RESULT));
		return false;
	}
}
