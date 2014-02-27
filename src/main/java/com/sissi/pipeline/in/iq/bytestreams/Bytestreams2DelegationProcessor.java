package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.StreamhostUsed;
import com.sissi.server.exchange.Delegation;
import com.sissi.server.exchange.ExchangerContext;

/**
 * @author kim 2014年2月24日
 */
public class Bytestreams2DelegationProcessor extends ProxyProcessor {

	private final Delegation delegation;

	private final StreamhostUsed streamhostUsed;

	private final ExchangerContext exchangerContext;

	public Bytestreams2DelegationProcessor(Delegation delegation, StreamhostUsed streamhostUsed, ExchangerContext exchangerContext) {
		super();
		this.delegation = delegation;
		this.streamhostUsed = streamhostUsed;
		this.exchangerContext = exchangerContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		try {
			this.delegation.read(this.exchangerContext.leave(protocol.parent().getId()));
		} catch (Exception e) {
//			e.printStackTrace();
		}
		context.write(protocol.cast(Bytestreams.class).setStreamhostUsed(this.streamhostUsed).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
