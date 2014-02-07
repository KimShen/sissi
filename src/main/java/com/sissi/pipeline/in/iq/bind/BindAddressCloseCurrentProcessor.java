package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;

/**
 * @author kim 2014年1月3日
 */
public class BindAddressCloseCurrentProcessor extends ProxyProcessor {

	private final Error error = new ServerError().add(Conflict.DETAIL_ELEMENT);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.resources(context.jid(), true).isEmpty() ? true : !context.write(Stream.closeWhenRunning(this.error)).close();
	}
}
