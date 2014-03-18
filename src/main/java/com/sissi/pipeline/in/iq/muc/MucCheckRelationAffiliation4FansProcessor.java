package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年3月14日
 */
public class MucCheckRelationAffiliation4FansProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(NotAllowed.DETAIL);

	private final RelationMucMapping mapping;

	public MucCheckRelationAffiliation4FansProcessor(RelationMucMapping mapping) {
		super();
		this.mapping = mapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		RelationMuc relation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			for (JID jid : this.mapping.mapping(group.resource(item.getNick()))) {
				if (ItemAffiliation.parse(super.ourRelation(jid, group).cast(RelationMuc.class).affiliation()).contains(relation.affiliation())) {
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
