package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XChange;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.protocol.muc.XReason;

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
		if (protocol.cast(Message.class).getBody().getText().startsWith("un")) {
			String[] params = protocol.cast(Message.class).getBody().getText().split(" ");
			IQ iq = new IQ();
			iq.setTo(super.build(protocol.getTo()));
			XMucAdmin xmuc = new XMucAdmin();
			XChange target = new XChange().setNick(params[1]).setRole(params[2]);
			target.set(null, new XReason().setText(params[3]));
			xmuc.set(null, target);
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
