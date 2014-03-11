package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.pipeline.in.iq.IQResponseProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月10日
 */
public class RegisterMucFieldsCheckAffiliationProcessor extends ProxyProcessor {

	private final IQResponseProcessor processor;

	public RegisterMucFieldsCheckAffiliationProcessor(IQResponseProcessor processor) {
		super();
		this.processor = processor;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return ItemAffiliation.NONE.equals(super.ourRelation(context.jid(), super.build(protocol.getTo())).cast(RelationMuc.class).affiliation()) ? true : this.processor.input(context, protocol.parent());
	}
}
