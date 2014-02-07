package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.bind.Bind;

/**
 * @author kim 2013-10-29
 */
public class BindingProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(IQ.class.cast(protocol.getParent().reply().clear().setType(ProtocolType.RESULT)).add(Bind.class.cast(protocol).clear().setJid(context.jid().asString())));
		return context.bind().binding();
	}
}
