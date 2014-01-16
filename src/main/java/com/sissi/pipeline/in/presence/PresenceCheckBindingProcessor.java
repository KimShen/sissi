package com.sissi.pipeline.in.presence;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAuthorized;

/**
 * @author kim 2014年1月14日
 */
public class PresenceCheckBindingProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isBinding() ? true : !context.write(Stream.closeWhenRunning(new ServerError().add(NotAuthorized.DETAIL_ELEMENT)).setFrom(context.getDomain()).setTo(protocol.getFrom())).close();
	}
}
