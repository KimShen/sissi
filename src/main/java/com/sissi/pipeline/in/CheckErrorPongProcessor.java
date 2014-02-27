package com.sissi.pipeline.in;

import java.util.HashSet;
import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2014年1月29日
 */
public class CheckErrorPongProcessor implements Input {

	private final Set<String> ignore;

	public CheckErrorPongProcessor(HashSet<String> ignore) {
		super();
		this.ignore = ignore;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.type(ProtocolType.ERROR) && (!protocol.to() || protocol.to(this.ignore)) ? this.pong(context, protocol) : true;
	}

	private boolean pong(JIDContext context, Protocol protocol) {
		context.pong(protocol.parent());
		return false;
	}
}
