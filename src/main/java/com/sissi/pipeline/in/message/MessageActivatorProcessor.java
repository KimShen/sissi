package com.sissi.pipeline.in.message;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月25日
 */
public class MessageActivatorProcessor implements Input {

	private final Addressing addressing;

	public MessageActivatorProcessor(Addressing addressing) {
		super();
		this.addressing = addressing;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.addressing.activate(context);
		return true;
	}
}
