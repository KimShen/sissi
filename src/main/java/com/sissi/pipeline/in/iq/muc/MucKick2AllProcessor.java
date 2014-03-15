package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XActor;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.protocol.muc.XTarget;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucStatusJudger;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年3月14日
 */
public class MucKick2AllProcessor extends ProxyProcessor {

	private final MucStatusJudger mucStatusJudger;

	private final MucConfigBuilder mucConfigBuilder;

	private final RelationMucMapping relationMucMapping;

	public MucKick2AllProcessor(RelationMucMapping mapping, MucStatusJudger mucStatusJudger, MucConfigBuilder mucConfigBuilder) {
		super();
		this.relationMucMapping = mapping;
		this.mucStatusJudger = mucStatusJudger;
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		XTarget target = protocol.cast(XMucAdmin.class).getItem();
		Item item = new Item().actor(new XActor(context.jid())).reason(target.getReason());
		Presence presence = new Presence().setType(PresenceType.UNAVAILABLE).setFrom(group.resource(target.getNick()));
		for (JID each : this.relationMucMapping.mapping(group)) {
			RelationMuc relation = super.ourRelation(each, group).cast(RelationMuc.class).noneRole();
			for (JID to : super.whoSubscribedMe(group)) {
				super.findOne(to, true).write(presence.reset().add(this.mucStatusJudger.judege(new XUser(to, config.allowed(to, MucConfig.HIDDEN_NATIVE, null)).item(item.hidden(config.allowed(to, MucConfig.HIDDEN_COMPUTER, each)).relation(relation))).cast(XUser.class)));
			}
		}
		return true;
	}
}
