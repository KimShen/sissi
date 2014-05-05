package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * 初始JIDContext.domain
 * 
 * @author kim 2014年1月5日
 */
public class StreamContextDomainProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.domain(protocol.cast(Stream.class).getTo());
		return true;
	}
}
