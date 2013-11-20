package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.login.Bind;

/**
 * @author kim 2013-10-29
 */
public class BindingProcessor extends UtilProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Bind bind = Bind.class.cast(protocol);
		this.binding(context, bind);
		context.write(this.prepareResponse(protocol, bind));
		return true;
	}

	private IQ prepareResponse(Protocol protocol, Bind bind) {
		IQ iq = (IQ) protocol.getParent().reply();
		iq.setType(Type.RESULT.toString());
		iq.clear();
		iq.add(bind);
		return iq;
	}

	private void binding(JIDContext context, Bind bind) {
		if (bind.hasResource()) {
			context.getJid().setResource(bind.getResource().getText());
		}
		bind.clear();
		bind.setJid(context.getJid().asString());
	}
}
