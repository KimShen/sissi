package com.sissi.pipeline.in.iq.muc.admin.role;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.relation.muc.role.RoleBuilder;

/**
 * 更新角色
 * 
 * @author kim 2014年3月14日
 */
public class MucSetRoleProcessor extends ProxyProcessor {

	private final RoleBuilder role;

	public MucSetRoleProcessor(RoleBuilder role) {
		super();
		this.role = role;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			this.role.build(item.getRole()).change(group.resource(item.getNick()));
		}
		return true;
	}
}
