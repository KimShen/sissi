package com.sissi.pipeline.in.security;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月2日
 */
public class BindedProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isBinding() ? true : this.notAuthorized(context, protocol);
	}

	private Boolean notAuthorized(JIDContext context, Protocol protocol) {
//		context.write(Stream.closeSuddenly(new ServerError().add(InvalidFrom.DETAIL)));
//		context.close();
		return false;
	}

}
