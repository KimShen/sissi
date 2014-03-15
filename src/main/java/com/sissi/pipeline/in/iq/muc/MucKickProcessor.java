package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年3月14日
 */
public class MucKickProcessor extends ProxyProcessor {

	private final RelationMucMapping mapping;

	public MucKickProcessor(RelationMucMapping mapping) {
		super();
		this.mapping = mapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo()).resource(protocol.cast(XMucAdmin.class).getItem().getNick());
		for (JID jid : this.mapping.mapping(group)) {
			super.remove(jid, group);
		}
		return true;
	}
}
