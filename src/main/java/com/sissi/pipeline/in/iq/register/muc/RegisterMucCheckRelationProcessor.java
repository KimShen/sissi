package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.pipeline.in.iq.IQResponseProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.relation.muc.MucRelation;

/**
 * 排斥者校验
 * 
 * @author kim 2014年3月10日
 */
public class RegisterMucCheckRelationProcessor extends ProxyProcessor {

	private final IQResponseProcessor proxy;

	public RegisterMucCheckRelationProcessor(IQResponseProcessor proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		MucRelation relation = super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(MucRelation.class);
		return relation.activate() && !relation.outcast() ? true : this.proxy.input(context, protocol.parent());
	}
}
