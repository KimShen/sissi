package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.JIDMalformed;

/**
 * 昵称有效性校验
 * 
 * @author kim 2014年2月11日
 */
public class PresenceMucCheckNicknameContainsProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.MODIFY).add(JIDMalformed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.build(protocol.getTo()).isBare() ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}

}
