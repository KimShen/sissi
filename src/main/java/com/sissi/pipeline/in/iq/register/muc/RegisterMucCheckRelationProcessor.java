package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.pipeline.in.iq.IQResponseProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月10日
 */
public class RegisterMucCheckRelationProcessor extends ProxyProcessor {

	private final IQResponseProcessor processor;

	public RegisterMucCheckRelationProcessor(IQResponseProcessor processor) {
		super();
		this.processor = processor;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		RelationMuc relation = super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(RelationMuc.class);
		return relation.activate() && !relation.outcast() ? true : this.processor.input(context, protocol.parent());
	}
}
