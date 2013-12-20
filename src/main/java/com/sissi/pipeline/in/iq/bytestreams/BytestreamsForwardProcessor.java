package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013年12月18日
 */
public class BytestreamsForwardProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext target = super.addressing.findOne(super.build(protocol.getParent().getTo()).getBare());
		target.write(protocol.getParent().setFrom(context.getJid().asStringWithBare()).setTo(target.getJid().asStringWithBare()).setType(Type.SET));
		return true;
	}
}
