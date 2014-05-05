package com.sissi.pipeline.in.iq.muc.admin.affiliation;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.MucRelationContext;

/**
 * 获取岗位列表
 * 
 * @author kim 2014年3月14日
 */
public class MucGetAffiliationProcessor extends ProxyProcessor {

	private final MucRelationContext relationContext;

	public MucGetAffiliationProcessor(MucRelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XMucAdmin admin = protocol.cast(XMucAdmin.class);
		JID group = super.build(admin.clear().parent().getTo());
		for (Relation relation : this.relationContext.myRelations(group, admin.affiliation())) {
			// Always not hidden, 永远显示JID
			admin.add(new Item(false, relation.cast(MucRelation.class)));
		}
		context.write(protocol.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
