package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.muc.MucApplyContext;
import com.sissi.ucenter.muc.MucRoleBuilder;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2ApplyResponseProcessor extends ProxyProcessor {

	private final XMuc muc = new XMuc();

	private final MucRoleBuilder mucRoleBuilder;

	private final Input proxy;

	public MessageMuc2ApplyResponseProcessor(MucRoleBuilder mucRoleBuilder, Input proxy) {
		super();
		this.mucRoleBuilder = mucRoleBuilder;
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XData data = protocol.cast(Message.class).getData();
		JID group = super.build(protocol.parent().getTo());
		JID to = super.build(data.findField(MucApplyContext.MUC_JID, XField.class).getValue().toString());
		this.mucRoleBuilder.build(data.findField(MucApplyContext.MUC_ROLE, XField.class).getValue().toString()).change(group.resource(super.ourRelation(to, group).name()));
		return this.proxy.input(super.findOne(to, true), new Presence().add(this.muc).setTo(group.resource(data.findField(MucApplyContext.MUC_NICKNAME, XField.class).getValue().toString())));
	}
}
