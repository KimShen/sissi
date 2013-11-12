package com.sissi.process.iq.bind;

import com.sissi.addressing.Addressing;
import com.sissi.context.Context;
import com.sissi.context.JID;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.bind.Bind;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-10-29
 */
public class BindProcessor implements Processor {

	private JID systemJID;

	private Addressing addressing;

	private Processor presenceNotifyProcessor;

	public BindProcessor(JID systemJID, Addressing addressing, Processor presenceNotifyProcessor) {
		super();
		this.systemJID = systemJID;
		this.addressing = addressing;
		this.presenceNotifyProcessor = presenceNotifyProcessor;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		Bind bind = Bind.class.cast(protocol);
		this.bindingHost(context);
		this.bindingResource(context, bind);
		this.bindingJID(context, bind);
		this.addressing.join(context);
		context.write(this.prepareResponse(protocol, bind));
		this.presenceNotifyProcessor.process(context, new Presence());
	}

	private IQ prepareResponse(Protocol protocol, Bind bind) {
		IQ iq = (IQ) protocol.getParent().reply();
		iq.setType(Type.RESULT.toString());
		iq.add(bind);
		return iq;
	}

	private void bindingJID(Context context, Bind bind) {
		bind.clear();
		bind.setJid(context.jid().asString());
	}

	private void bindingResource(Context context, Bind bind) {
		if (bind.hasResource()) {
			context.jid().setResource(bind.getResource().getText());
		}
	}

	private void bindingHost(Context context) {
		context.jid().setHost(this.systemJID.getHost());
	}
}
