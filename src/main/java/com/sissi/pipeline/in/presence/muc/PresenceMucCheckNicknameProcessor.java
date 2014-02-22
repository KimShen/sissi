package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.JIDMalformed;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucCheckNicknameProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("400").setType(ProtocolType.MODIFY).add(JIDMalformed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.to() && !super.build(protocol.getTo()).isBare() ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(this.error));
		return false;
	}

}
