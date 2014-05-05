package com.sissi.pipeline.in.iq.muc.admin.role;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.MucRelationMapping;

/**
 * 角色越权校验(Set)
 * 
 * @author kim 2014年3月14日
 */
public class MucCheckRole4FansProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.AUTH).add(NotAllowed.DETAIL);

	private final MucRelationMapping mapping;

	public MucCheckRole4FansProcessor(MucRelationMapping mapping) {
		super();
		this.mapping = mapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		MucRelation relation = super.ourRelation(context.jid(), group).cast(MucRelation.class);
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			// 同Nickname多JID
			for (JID jid : this.mapping.mapping(group.resource(item.getNick()))) {
				if (ItemRole.parse(super.ourRelation(jid, group).cast(MucRelation.class).role()).contains(relation.role())) {
					return this.writeAndReturn(context, protocol);
				}
			}
		}
		return true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
