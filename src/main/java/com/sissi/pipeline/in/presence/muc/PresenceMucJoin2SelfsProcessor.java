package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfsProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		for (Relation each : super.myRelations(group)) {
			RelationMuc relation = RelationMuc.class.cast(each);
			super.broadcast(context.jid(), group.resource(relation.getName()), Presence.class.cast(protocol).clear().add(new XUser().add(new Item(relation))));
		}
		return true;
	}
}
