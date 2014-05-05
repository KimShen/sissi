package com.sissi.pipeline.in.iq.muc.admin.affiliation;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.MucRelationContext;

/**
 * 岗位Owner移交限制校验
 * 
 * @author kim 2014年3月14日
 */
public class MucCheckAffiliationOwnerProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(Conflict.DETAIL_ELEMENT);

	private final MucRelationContext relationContext;

	public MucCheckAffiliationOwnerProcessor(MucRelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		return context.jid().like(protocol.cast(XMucAdmin.class).first().getJid()) && ItemAffiliation.OWNER.equals(this.relationContext.ourRelation(context.jid(), group).cast(MucRelation.class).affiliation()) ? this.relationContext.myRelations(group, ItemAffiliation.OWNER.toString()).size() <= 1 ? this.writeAndReturn(context, protocol) : true : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
