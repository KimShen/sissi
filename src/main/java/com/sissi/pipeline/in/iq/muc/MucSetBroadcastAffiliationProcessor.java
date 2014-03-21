package com.sissi.pipeline.in.iq.muc;

import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.protocol.muc.XMucAdminAction;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucRelationContext;
import com.sissi.ucenter.muc.MucStatusJudger;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月14日
 */
public class MucSetBroadcastAffiliationProcessor extends ProxyProcessor {

	private final MucStatusJudger mucStatusJudger;

	private final MucConfigBuilder mucConfigBuilder;

	private final MucRelationContext mucRelationContext;

	public MucSetBroadcastAffiliationProcessor(MucStatusJudger mucStatusJudger, MucConfigBuilder mucConfigBuilder, MucRelationContext mucRelationContext) {
		super();
		this.mucStatusJudger = mucStatusJudger;
		this.mucConfigBuilder = mucConfigBuilder;
		this.mucRelationContext = mucRelationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			JID each = super.build(item.getJid());
			for (Relation relation : this.mucRelationContext.ourRelations(each, group)) {
				for (JID to : super.whoSubscribedMe(group)) {
					super.findOne(to, true).write(item.compare(config.pull(MongoConfig.FIELD_AFFILIATION, String.class)).presence(XMucAdminAction.AFFILIATION, group.resource(relation.name())).reset().add(this.mucStatusJudger.judege(new XUser(group, to, config.allowed(to, MucConfig.HIDDEN_NATIVE, null)).item(item.hidden(config.allowed(to, MucConfig.HIDDEN_COMPUTER, each)).relation(relation.cast(RelationMuc.class).affiliation(item.getAffiliation())))).cast(XUser.class)));
				}
			}
		}
		return true;
	}
}
