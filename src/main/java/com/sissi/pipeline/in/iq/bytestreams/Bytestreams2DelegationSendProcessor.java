package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.server.exchange.Delegation;
import com.sissi.server.exchange.ExchangerContext;

/**
 * @author kim 2014年2月24日
 */
public class Bytestreams2DelegationSendProcessor extends ProxyProcessor {

	private final Delegation delegation;

	private final ExchangerContext exchangerContext;

	public Bytestreams2DelegationSendProcessor(Delegation delegation, ExchangerContext exchangerContext) {
		super();
		this.delegation = delegation;
		this.exchangerContext = exchangerContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.delegation.recover(this.exchangerContext.active(protocol.parent().getId()));
		return true;
	}
}
