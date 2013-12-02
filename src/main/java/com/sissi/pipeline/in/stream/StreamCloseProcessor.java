package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class StreamCloseProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isSession() ? false : true;
	}
}
