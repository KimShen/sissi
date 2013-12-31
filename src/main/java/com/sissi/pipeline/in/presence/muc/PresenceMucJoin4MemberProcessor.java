package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.XUser;
import com.sissi.ucenter.MucContext;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2013年12月30日
 */
public class PresenceMucJoin4MemberProcessor extends ProxyProcessor {

	private final MucContext mucContext;

	public PresenceMucJoin4MemberProcessor(MucContext mucContext) {
		super();
		this.mucContext = mucContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID muc = super.build(protocol.getTo()).getBare();
		for (Relation relation : this.mucContext.myMembers(muc)) {
			RelationMuc mucRelation = RelationMuc.class.cast(relation);
			context.write(new Presence().add(new XUser(mucRelation.getMember(), mucRelation.getRole(), mucRelation.getAffiliation())).setFrom(new StringBuffer(muc.asStringWithBare()).append("/").append(mucRelation.getName()).toString()).setTo(context.getJid().getBare()));
		}
		return true;
	}
}
