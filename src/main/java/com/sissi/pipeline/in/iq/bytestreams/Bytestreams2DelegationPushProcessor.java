package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.server.exchange.Delegation;
import com.sissi.server.exchange.ExchangerContext;

/**
 * 离线文件推送
 * 
 * @author kim 2014年2月24日
 */
public class Bytestreams2DelegationPushProcessor extends ProxyProcessor {

	private final Delegation delegation;

	private final ExchangerContext exchangerContext;

	public Bytestreams2DelegationPushProcessor(Delegation delegation, ExchangerContext exchangerContext) {
		super();
		this.delegation = delegation;
		this.exchangerContext = exchangerContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.delegation.push(this.exchangerContext.activate(protocol.parent().getId()));
		return true;
	}
}
