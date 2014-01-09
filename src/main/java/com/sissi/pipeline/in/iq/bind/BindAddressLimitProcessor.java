package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.element.ResourceConstraint;

/**
 * @author kim 2014年1月6日
 */
public class BindAddressLimitProcessor extends ProxyProcessor {

	private Integer resources;

	public BindAddressLimitProcessor(Integer resources) {
		super();
		this.resources = resources;

	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return super.others(context) < this.resources ? true : this.close(context, protocol);
	}

	private Boolean close(JIDContext context, Protocol protocol) {
		return !context.write(protocol.getParent().clear().setFrom(context.getDomain()).setTo(context.getJid().asString()).setError(new ServerError().setType(Type.CANCEL.toString()).add(ResourceConstraint.DETAIL))).write(Stream.closeGracefully()).close();
	}
}
