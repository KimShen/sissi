package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * 关闭XMPP流
 * 
 * @author kim 2013-10-24
 */
public class StreamCloseProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Stream.class).consumed() ? !context.write(Stream.closeGraceFully()).closePrepare() : true;
	}
}
