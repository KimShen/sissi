package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * @author kim 2013-10-24
 */
public class StreamCloseProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return Stream.class.cast(protocol).isUsing() ? this.close(context) : true;
	}

	private Boolean close(JIDContext context) {
		context.write(Stream.closeGracefully());
		context.closePrepare();
		return false;
	}
}
