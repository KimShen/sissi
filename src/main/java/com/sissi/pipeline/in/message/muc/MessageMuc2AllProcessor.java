package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2AllProcessor extends ProxyProcessor {

	private Input input;

	public MessageMuc2AllProcessor(Input input) {
		super();
		this.input = input;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (protocol.cast(Message.class).getBody().getText().startsWith("set")) {
			String[] params = protocol.cast(Message.class).getBody().getText().split(" ");
			IQ iq = new IQ();
			iq.setTo(super.build(protocol.getTo())).setType(ProtocolType.SET);
			XMucAdmin xmuc = new XMucAdmin();
			for (char each : params[1].toCharArray()) {
				xmuc.set(null, new Item().nick(each + "").role(params[2]).reason(params[3]));
			}
			iq.set(null, xmuc);
			this.input.input(context, xmuc);
		}
		if (protocol.cast(Message.class).getBody().getText().startsWith("get")) {
			String[] params = protocol.cast(Message.class).getBody().getText().split(" ");
			IQ iq = new IQ();
			iq.setTo(super.build(protocol.getTo())).setType(ProtocolType.GET);
			;
			XMucAdmin xmuc = new XMucAdmin();
			xmuc.set(null, new Item().role(params[1]));
			iq.set(null, xmuc);
			this.input.input(context, xmuc);
		}

		JID group = super.build(protocol.getTo());
		protocol.setFrom(group.resource(super.ourRelation(context.jid(), group).name()));
		for (JID jid : super.whoSubscribedMe(group)) {
			super.findOne(jid, true).write(protocol);
		}
		return true;
	}
}
