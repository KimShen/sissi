package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.muc.Destory;
import com.sissi.protocol.muc.Owner;

/**
 * @author kim 2014年3月24日
 */
public class MucOwnerSetCancelProcessor extends ProxyProcessor {

	private final Input proxy;

	//TODO
	public MucOwnerSetCancelProcessor(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.proxy.input(context, new IQ().add(new Owner().destory(new Destory())).setTo(protocol.parent().getTo()).setType(ProtocolType.SET));
	}
}
