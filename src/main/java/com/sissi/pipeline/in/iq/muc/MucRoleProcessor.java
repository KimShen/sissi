package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.XChange;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.muc.MucRoleBuilder;

/**
 * @author kim 2014年3月14日
 */
public class MucRoleProcessor extends ProxyProcessor {

	private final MucRoleBuilder mucRoleBuilder;

	public MucRoleProcessor(MucRoleBuilder mucRoleBuilder) {
		super();
		this.mucRoleBuilder = mucRoleBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XChange change = protocol.cast(XMucAdmin.class).getItem();
		this.mucRoleBuilder.build(change.getRole()).change(super.build(protocol.parent().getTo()).resource(change.getNick()));
		return true;
	}
}
