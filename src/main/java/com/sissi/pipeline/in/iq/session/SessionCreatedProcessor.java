package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月3日
 */
public class SessionCreatedProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.setSession(true);
		return true;
	}
}
