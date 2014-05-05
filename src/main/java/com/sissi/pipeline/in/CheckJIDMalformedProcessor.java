package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.JIDMalformed;

/**
 * JID有效性校验
 * 
 * @author kim 2014年1月24日
 */
public class CheckJIDMalformedProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.MODIFY).add(JIDMalformed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() ? true : super.build(protocol.getTo()).valid() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
