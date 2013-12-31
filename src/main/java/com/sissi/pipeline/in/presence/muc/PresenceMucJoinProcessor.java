package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.MucComputer;
import com.sissi.ucenter.relation.MucWrapRelation;

/**
 * @author kim 2013年12月29日
 */
public class PresenceMucJoinProcessor extends ProxyProcessor {

	private final MucComputer computer;

	public PresenceMucJoinProcessor(MucComputer computer) {
		super();
		this.computer = computer;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.establish(context.getJid(), this.computer.computer(new MucWrapRelation(context.getJid(), super.build(protocol.getTo()))));
		return true;
	}
}
