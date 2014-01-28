package com.sissi.pipeline.in.presence;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月14日
 */
public class PresenceCheckTypeProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return PresenceType.parse(protocol.getType()) != null ? true : this.writeAndReturn(context, protocol);
	}
	
	private Boolean writeAndReturn(JIDContext context, Protocol protocol){
		context.write(protocol.reply().setError(new ServerError().setType(ProtocolType.CANCEL).add(BadRequest.DETAIL)));
		return false;
	}
}
