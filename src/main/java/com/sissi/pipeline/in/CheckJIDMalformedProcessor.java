package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.JIDMalformed;

/**
 * @author kim 2014年1月24日
 */
public class CheckJIDMalformedProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.MODIFY).add(JIDMalformed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() ? true : (super.build(protocol.getTo()).valid() ? true : this.writeAndReturn(context, protocol));
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().clear().reply().setError(this.error));
		return false;
	}
}
