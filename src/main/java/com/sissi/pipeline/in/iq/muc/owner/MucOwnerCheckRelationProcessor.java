package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.muc.MucRelation;

/**
 * 岗位校验(OWNER,并且房间已激活)
 * 
 * @author kim 2014年3月27日
 */
public class MucOwnerCheckRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.AUTH).add(Forbidden.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		MucRelation relation = super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(MucRelation.class);
		return relation.activate() && ItemAffiliation.OWNER.equals(relation.affiliation()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
