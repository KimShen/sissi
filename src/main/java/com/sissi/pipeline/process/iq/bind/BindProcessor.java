package com.sissi.pipeline.process.iq.bind;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.bind.Bind;

/**
 * @author kim 2013-10-29
 */
public class BindProcessor implements ProcessPipeline {

	private JID systemJID;

	private Addressing addressing;

	public BindProcessor(JID systemJID, Addressing addressing) {
		super();
		this.systemJID = systemJID;
		this.addressing = addressing;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		Bind bind = Bind.class.cast(protocol);
		this.bindingHost(context);
		this.bindingResource(context, bind);
		this.bindingJID(context, bind);
		this.addressing.join(context);
		context.write(this.prepareResponse(protocol, bind));
		return true;
	}

	private IQ prepareResponse(Protocol protocol, Bind bind) {
		IQ iq = (IQ) protocol.getParent().reply();
		iq.setType(Type.RESULT.toString());
		iq.add(bind);
		return iq;
	}

	private void bindingJID(JIDContext context, Bind bind) {
		bind.clear();
		bind.setJid(context.jid().asString());
	}

	private void bindingResource(JIDContext context, Bind bind) {
		if (bind.hasResource()) {
			context.jid().resource(bind.getResource().getText());
		}
	}

	private void bindingHost(JIDContext context) {
		context.jid().host(this.systemJID.host());
	}
}
