package com.sissi.pipeline.in.presence;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.presence.Presence;

/**
 * 类型合法性校验
 * 
 * @author kim 2014年1月14日
 */
public class PresenceCheckTypeProcessor implements Input {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Presence.class).valid() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.reply().setError(this.error));
		return false;
	}
}
