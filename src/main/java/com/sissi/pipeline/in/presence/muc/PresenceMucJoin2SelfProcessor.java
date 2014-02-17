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
import com.sissi.ucenter.RelationMucMapping;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfProcessor extends ProxyProcessor {

	private final RelationMucMapping relationMucMapping;

	public PresenceMucJoin2SelfProcessor(RelationMucMapping relationMucMapping) {
		super();
		this.relationMucMapping = relationMucMapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		Presence presence = new Presence();
		for (Relation each : super.myRelations(group)) {
			RelationMuc muc = RelationMuc.class.cast(each);
			context.write(presence.clear().add(new XUser().add(new Item(muc))).clauses(super.findOne(this.relationMucMapping.mapping(group.resource(muc.getName()))).status().clauses()).setFrom(group));
		}
		return true;
	}
}
