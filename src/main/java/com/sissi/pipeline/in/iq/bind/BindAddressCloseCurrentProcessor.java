package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;

/**
 * @author kim 2014年1月3日
 */
public class BindAddressCloseCurrentProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return super.others(context, true) == 0 ? true : !context.write(Stream.closeWhenRunning(new ServerError().add(Conflict.DETAIL_ELEMENT))).close();
	}
}
