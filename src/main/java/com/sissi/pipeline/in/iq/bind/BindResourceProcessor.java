package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.bind.Bind;

/**
 * @author kim 2013-10-29
 */
public class BindResourceProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(IQ.class.cast(protocol.getParent().reply().clear().setType(Type.RESULT)).add(this.binding(context, Bind.class.cast(protocol))));
		return true;
	}

	private Bind binding(JIDContext context, Bind bind) {
		if (bind.hasResource()) {
			context.getJid().setResource(bind.getResource().getText());
		}
		return bind.clear().setJid(context.getJid().asString());
	}
}
