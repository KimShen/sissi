package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;

/**
 * @author kim 2013年12月19日
 */
public class BytestreamsUsedProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext target = super.addressing.findOne(super.build(protocol.getParent().getTo()).getBare());
		Bytestreams.class.cast(protocol).setMode(null);
		target.write(protocol.getParent().setTo(target.getJid().asStringWithBare()).setFrom(context.getJid().asStringWithBare()));
		return true;
	}

}
