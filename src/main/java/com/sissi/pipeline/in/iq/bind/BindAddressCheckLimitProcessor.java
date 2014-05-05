package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ResourceConstraint;

/**
 * JID最大资源数量校验
 * 
 * @author kim 2014年1月6日
 */
public class BindAddressCheckLimitProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(ResourceConstraint.DETAIL_ELEMENT);

	private final int limit;

	/**
	 * @param limit 资源数量
	 */
	public BindAddressCheckLimitProcessor(int limit) {
		super();
		this.limit = limit;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.resources(context.jid()).lessThan(this.limit) ? true : !context.write(protocol.parent().reply().setFrom(context.domain()).setError(this.error)).write(Stream.closeGraceFully()).close();
	}
}
