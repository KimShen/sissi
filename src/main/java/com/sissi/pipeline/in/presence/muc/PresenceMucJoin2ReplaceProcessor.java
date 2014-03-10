package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年3月7日
 */
public class PresenceMucJoin2ReplaceProcessor extends ProxyProcessor {

	private final Input proxy;

	public PresenceMucJoin2ReplaceProcessor(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		Relation relation = super.ourRelation(context.jid(), group);
		if (!group.resource().equals(relation.name())) {
			this.proxy.input(context, new Presence().setTo(group.resource(relation.name())));
		}
		return true;
	}
}
