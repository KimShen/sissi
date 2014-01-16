package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ResourceConstraint;

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
		return super.others(context) < this.resources ? true : context.write(protocol.getParent().clear().setFrom(context.getDomain()).setTo(context.getJid().asString()).setError(new ServerError().setType(ProtocolType.CANCEL).add(ResourceConstraint.DETAIL_ELEMENT))).write(Stream.close()).close();
	}
}
