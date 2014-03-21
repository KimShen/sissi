package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ItemNotFound;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.user.VCardContext;

/**
 * @author kim 2014年3月14日
 */
public class MucCheckRelationAffiliation4FansProcessor extends ProxyProcessor {

	private final Error notAllowed = new ServerError().setType(ProtocolType.AUTH).add(NotAllowed.DETAIL);

	private final Error itemNotFound = new ServerError().setType(ProtocolType.CANCEL).add(ItemNotFound.DETAIL);

	private final VCardContext vcardContext;

	public MucCheckRelationAffiliation4FansProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		RelationMuc relation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			JID each = super.build(item.getJid());
			if (item.error(this.vcardContext.exists(each) ? null : this.itemNotFound) || item.error(ItemAffiliation.parse(super.ourRelation(each, group).cast(RelationMuc.class).affiliation()).contains(relation.affiliation()) ? this.notAllowed : null)) {
				return this.writeAndReturn(context, protocol, item.error());
			}
		}
		return true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol, Error error) {
		context.write(protocol.parent().reply().setError(error));
		return false;
	}
}
