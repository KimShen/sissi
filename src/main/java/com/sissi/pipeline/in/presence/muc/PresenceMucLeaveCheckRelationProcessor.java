package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月18日
 */
public class PresenceMucLeaveCheckRelationProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return RelationMuc.class.cast(super.ourRelation(context.jid(), super.build(protocol.getTo()))).isActivate() ? true : false;
	}
}
