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
public class BindResponseProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setType(ProtocolType.RESULT).cast(IQ.class).add(protocol.cast(Bind.class).clear().setJid(context.jid().asString())));
		return context.bind().binding();
	}
}
