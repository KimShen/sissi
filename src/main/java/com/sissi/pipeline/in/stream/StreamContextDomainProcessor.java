package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * @author kim 2014年1月5日
 */
public class StreamContextDomainProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.setDomain(Stream.class.cast(protocol).getTo());
		return true;
	}
}
