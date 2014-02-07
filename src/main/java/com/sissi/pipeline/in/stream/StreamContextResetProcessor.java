package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月2日
 */
public class StreamContextResetProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.reset();
		return true;
	}
}
