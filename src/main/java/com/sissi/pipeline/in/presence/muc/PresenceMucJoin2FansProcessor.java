package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.MucGroupContext;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2FansProcessor extends ProxyProcessor {

	private final MucGroupContext mucGroupContext;

	public PresenceMucJoin2FansProcessor(MucGroupContext mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		RelationMuc ourRelation = RelationMuc.class.cast(super.ourRelation(context.jid(), group));
		Presence presence = new Presence();
		for (Relation each : super.myRelations(group)) {
			RelationMuc muc = RelationMuc.class.cast(each);
			JID to = super.build(muc.getJID());
			super.findOne(to, true).write(presence.clear().add(new XUser(to.asString()).add(new Item(group, ourRelation, this.mucGroupContext).setJid(context.jid()))).clauses(context.status().clauses()).setFrom(protocol.getTo()));
		}
		return true;
	}
}
