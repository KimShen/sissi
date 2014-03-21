package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucRelationContext;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月14日
 */
public class MucGetRoleProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	private final MucRelationContext mucRelationContext;

	public MucGetRoleProcessor(MucConfigBuilder mucConfigBuilder, MucRelationContext mucRelationContext) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
		this.mucRelationContext = mucRelationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XMucAdmin admin = protocol.cast(XMucAdmin.class);
		JID group = super.build(admin.clear().parent().getTo());
		MucConfig config = mucConfigBuilder.build(group);
		for (Relation relation : this.mucRelationContext.myRelations(group, admin.role())) {
			admin.add(new Item(config.allowed(context.jid(), MucConfig.HIDDEN_COMPUTER, group.resource(relation.name())), relation.cast(RelationMuc.class)));
		}
		context.write(protocol.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
