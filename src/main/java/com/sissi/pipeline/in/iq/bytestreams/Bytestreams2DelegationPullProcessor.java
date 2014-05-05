package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.StreamhostUsed;

/**
 * 离线文件接收
 * 
 * @author kim 2014年2月24日
 */
public class Bytestreams2DelegationPullProcessor extends ProxyProcessor {

	private final StreamhostUsed streamhostUsed;

	public Bytestreams2DelegationPullProcessor(StreamhostUsed streamhostUsed) {
		super();
		this.streamhostUsed = streamhostUsed;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Bytestreams.class).setStreamhostUsed(this.streamhostUsed).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
