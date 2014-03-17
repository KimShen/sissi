package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.XChange;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucStatusJudger;
import com.sissi.ucenter.muc.RelationMuc;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年3月14日
 */
public class MucRoleBroadcastProcessor extends ProxyProcessor {

	private final MucStatusJudger mucStatusJudger;

	private final MucConfigBuilder mucConfigBuilder;

	private final RelationMucMapping relationMucMapping;

	public MucRoleBroadcastProcessor(MucStatusJudger mucStatusJudger, MucConfigBuilder mucConfigBuilder, RelationMucMapping relationMucMapping) {
		super();
		this.mucStatusJudger = mucStatusJudger;
		this.mucConfigBuilder = mucConfigBuilder;
		this.relationMucMapping = relationMucMapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		XChange change = protocol.cast(XMucAdmin.class).getItem();
		for (JID each : this.relationMucMapping.mapping(change.group(group))) {
			RelationMuc relation = super.ourRelation(each, group).cast(RelationMuc.class).role(change.getRole());
			for (JID to : super.whoSubscribedMe(group)) {
				super.findOne(to, true).write(change.presence().reset().add(this.mucStatusJudger.judege(new XUser(to, config.allowed(to, MucConfig.HIDDEN_NATIVE, null)).item(change.item(context.jid()).hidden(config.allowed(to, MucConfig.HIDDEN_COMPUTER, each)).relation(relation))).cast(XUser.class)));
			}
		}
		return true;
	}
}
