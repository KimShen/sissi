package com.sissi.pipeline.in.auth.impl;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class AuthNoneRepeatProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isLogining() ? false : true;
	}
}
