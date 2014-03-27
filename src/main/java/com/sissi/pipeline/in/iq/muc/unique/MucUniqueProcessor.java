package com.sissi.pipeline.in.iq.muc.unique;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.Unique;

/**
 * @author kim 2014年3月24日
 */
public class MucUniqueProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Unique.class).unique().parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
