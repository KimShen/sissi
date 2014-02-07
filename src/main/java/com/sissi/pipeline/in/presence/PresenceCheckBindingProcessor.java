package com.sissi.pipeline.in.presence;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAuthorized;

/**
 * @author kim 2014年1月14日
 */
public class PresenceCheckBindingProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.binding() ? true : !context.write(Stream.closeWhenRunning(new ServerError().setType(ProtocolType.AUTH).add(NotAuthorized.DETAIL_ELEMENT)).setFrom(context.domain())).close();
	}
}
