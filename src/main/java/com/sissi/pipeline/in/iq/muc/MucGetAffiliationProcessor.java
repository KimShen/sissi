package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucRelationContext;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月14日
 */
public class MucGetAffiliationProcessor extends ProxyProcessor {

	private final MucRelationContext mucRelationContext;

	public MucGetAffiliationProcessor(MucRelationContext mucRelationContext) {
		super();
		this.mucRelationContext = mucRelationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XMucAdmin admin = protocol.cast(XMucAdmin.class);
		JID group = super.build(admin.clear().parent().getTo());
		for (Relation relation : this.mucRelationContext.myRelations(group, admin.affiliation())) {
			admin.add(new Item(false, relation.cast(RelationMuc.class)));
		}
		context.write(protocol.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
