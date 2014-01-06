package com.sissi.pipeline.in.security;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.stream.NotAuthorized;

/**
 * @author kim 2014年1月2日
 */
public class BindedProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isBinding() ? true : this.notAuthorized(context, protocol);
	}

	private Boolean notAuthorized(JIDContext context, Protocol protocol) {
		context.write(Stream.closeSuddenly(new ServerError().add(NotAuthorized.DETAIL)));
		context.close();
		return false;
	}

}
