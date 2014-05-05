package com.sissi.pipeline.in.iq.muc.owner.cancel;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.muc.Destory;
import com.sissi.protocol.muc.Owner;

/**
 * 取消配置,销毁房间
 * 
 * @author kim 2014年3月24日
 */
public class MucOwnerCancelProcessor extends ProxyProcessor {

	private final String tip;

	private final Input proxy;

	public MucOwnerCancelProcessor(String tip, Input proxy) {
		super();
		this.tip = tip;
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.proxy.input(context, new Owner().destory(new Destory().reason(this.tip)).parent(new IQ().setTo(protocol.parent().getTo()).setType(ProtocolType.SET)));
	}
}
