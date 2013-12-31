package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.XUser;
import com.sissi.protocol.presence.XUserStatus;
import com.sissi.ucenter.MucComputer;
import com.sissi.ucenter.relation.MucWrapRelation;

/**
 * @author kim 2013年12月30日
 */
public class PresenceMucJoin2SelfProcessor extends ProxyProcessor {

	private final MucComputer computer;

	public PresenceMucJoin2SelfProcessor(MucComputer computer) {
		super();
		this.computer = computer;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		MucWrapRelation newRelation = MucWrapRelation.class.cast(this.computer.computer(new MucWrapRelation(context.getJid(), this.jidBuilder.build(protocol.getTo()))));
		context.write(new Presence().add(new XUser(newRelation.getMember(), newRelation.getRole(), newRelation.getAffiliation()).add(XUserStatus.STATUS_101)).setFrom(protocol.getTo()).setTo(context.getJid().getBare()));
		return true;
	}
}
