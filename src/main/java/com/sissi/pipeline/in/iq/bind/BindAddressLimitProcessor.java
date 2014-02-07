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
 * @author kim 2014年1月6日
 */
public class BindAddressLimitProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(ResourceConstraint.DETAIL_ELEMENT);

	private final int resources;

	public BindAddressLimitProcessor(int resources) {
		super();
		this.resources = resources;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.resources(context.jid()).lessThan(this.resources) ? true : !context.write(protocol.getParent().clear().reply().setFrom(context.domain()).setError(this.error)).write(Stream.closeGraceFully()).close();
	}
}
