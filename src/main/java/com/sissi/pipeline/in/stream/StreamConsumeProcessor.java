package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * 开启XMPP流
 * 
 * @author kim 2013年12月3日
 */
public class StreamConsumeProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.cast(Stream.class).consume();
		return true;
	}

}
