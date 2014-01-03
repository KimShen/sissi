package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.stream.Conflict;

/**
 * @author kim 2014年1月3日
 */
public class BindAddressCloseCurrentProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return super.others(context, true) == 0 ? true : this.close(context, protocol);
	}

	private Boolean close(JIDContext context, Protocol protocol) {
		context.write(new ServerError().add(Conflict.DETAIL).setFrom(protocol.getTo()).setTo(protocol.getFrom()));
		return false;
	}
}
