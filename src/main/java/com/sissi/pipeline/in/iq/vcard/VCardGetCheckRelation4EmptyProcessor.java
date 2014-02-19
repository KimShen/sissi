package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月26日
 */
public class VCardGetCheckRelation4EmptyProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.TO.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.getParent().to() || protocol.getParent().to(context.jid().asStringWithBare()) || RelationRoster.class.cast(super.ourRelation(context.jid(), super.build(protocol.getParent().getTo()))).in(this.relations) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.clear().getParent().reply().setType(ProtocolType.RESULT));
		return false;
	}

}
