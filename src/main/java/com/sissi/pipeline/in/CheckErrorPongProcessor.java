package com.sissi.pipeline.in;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2014年1月29日
 */
public class CheckErrorPongProcessor implements Input {

	private final List<String> ignore;

	public CheckErrorPongProcessor(List<String> ignore) {
		super();
		this.ignore = ignore;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.type(ProtocolType.ERROR) && (!protocol.to() || protocol.to(this.ignore)) ? this.pong(context, protocol) : true;
	}

	private boolean pong(JIDContext context, Protocol protocol) {
		context.pong(protocol.getParent());
		return false;
	}
}
