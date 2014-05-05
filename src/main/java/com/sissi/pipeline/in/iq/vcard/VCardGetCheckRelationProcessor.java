package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.CheckRelationProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年2月24日
 */
abstract class VCardGetCheckRelationProcessor extends CheckRelationProcessor {

	public VCardGetCheckRelationProcessor(boolean shortcut) {
		super(shortcut);
	}

	/*
	 * 1,不含To</p>2,To等于当前JID.bare</p>3,super.outRelation
	 * 
	 * @see com.sissi.pipeline.in.CheckRelationProcessor#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.parent().to() || protocol.parent().to(context.jid().asStringWithBare()) || super.ourRelation(context, protocol) ? true : this.writeAndReturn(context, protocol);
	}
}
