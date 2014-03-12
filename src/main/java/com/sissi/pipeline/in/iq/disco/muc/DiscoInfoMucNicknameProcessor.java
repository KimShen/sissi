package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.Disco;
import com.sissi.protocol.iq.disco.Identity;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年3月12日
 */
public class DiscoInfoMucNicknameProcessor extends ProxyProcessor {

	private final Identity identity;

	public DiscoInfoMucNicknameProcessor(Identity identity) {
		super();
		this.identity = identity;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Relation relation = super.ourRelation(context.jid(), super.build(protocol.parent().getTo()));
		if (relation.activate()) {
			protocol.cast(Disco.class).add(this.identity.clone().setName(relation.name()));
		}
		context.write(protocol.parent().setType(ProtocolType.RESULT).reply());
		return true;
	}
}
