package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2FansProcessor extends ProxyProcessor {

	private final RelationMucMapping relationMucMapping;

	public MessageMuc2FansProcessor(RelationMucMapping relationMucMapping) {
		super();
		this.relationMucMapping = relationMucMapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		protocol.setFrom(group.clone().resource(super.ourRelation(context.jid(), group).name()));
		for (JID jid : this.relationMucMapping.mapping(group)) {
			super.findOne(jid, true).write(protocol);
		}
		return true;
	}
}
