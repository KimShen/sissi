package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.XUser;
import com.sissi.protocol.presence.Presence.Type;
import com.sissi.ucenter.MucComputer;
import com.sissi.ucenter.MucContext;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;
import com.sissi.ucenter.relation.MucWrapRelation;

/**
 * @author kim 2013年12月31日
 */
public class PresenceMucLeave2MemberProcessor extends ProxyProcessor {

	private final MucContext mucContext;

	private final MucComputer computer;

	public PresenceMucLeave2MemberProcessor(MucContext mucContext, MucComputer computer) {
		super();
		this.mucContext = mucContext;
		this.computer = computer;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID muc = super.build(protocol.getTo()).getBare();
		MucWrapRelation newRelation = MucWrapRelation.class.cast(this.computer.computer(new MucWrapRelation(context.getJid(), this.jidBuilder.build(protocol.getTo()))));
		for (Relation relation : this.mucContext.myMembers(muc)) {
			RelationMuc mucRelation = RelationMuc.class.cast(relation);
			JID member = super.build(mucRelation.getMember());
			super.broadcast(member.getBare(), new Presence().setType(Type.UNAVAILABLE).add(new XUser(newRelation.getMember(), newRelation.getRole(), newRelation.getAffiliation())).setFrom(protocol.getTo()).setTo(member.getBare()));
		}
		return true;
	}
}
