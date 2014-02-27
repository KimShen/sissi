package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.StreamhostUsed;

/**
 * @author kim 2014年2月24日
 */
public class Bytestreams2DelegationReceiveProcessor extends ProxyProcessor {

	private final StreamhostUsed streamhostUsed;

	public Bytestreams2DelegationReceiveProcessor(StreamhostUsed streamhostUsed) {
		super();
		this.streamhostUsed = streamhostUsed;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Bytestreams.class).setStreamhostUsed(this.streamhostUsed).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
