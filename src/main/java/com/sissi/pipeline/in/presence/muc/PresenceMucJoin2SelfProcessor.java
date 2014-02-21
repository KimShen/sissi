package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.MucGroupContext;
import com.sissi.ucenter.MucStatusCollector;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;
import com.sissi.ucenter.RelationMucMapping;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfProcessor extends ProxyProcessor {

	private final RelationMucMapping relationMucMapping;

	private final MucStatusCollector mucStatusCollector;

	private final MucGroupContext mucGroupContext;

	public PresenceMucJoin2SelfProcessor(RelationMucMapping relationMucMapping, MucStatusCollector mucStatusCollector, MucGroupContext mucGroupContext) {
		super();
		this.relationMucMapping = relationMucMapping;
		this.mucStatusCollector = mucStatusCollector;
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		Presence presence = new Presence();
		for (Relation each : super.myRelations(group)) {
			RelationMuc muc = RelationMuc.class.cast(each);
			context.write(presence.clear().add(new XUser(context.jid().asString()).setItem(new Item(group, context.jid(), super.build(muc.getJID()), muc, this.mucGroupContext), this.mucStatusCollector)).clauses(super.findOne(this.relationMucMapping.mapping(group.resource(muc.getName()))).status().clauses()).setFrom(group));
		}
		return true;
	}
}
