package com.sissi.pipeline.in.message;

import com.sissi.addressing.AddressingActivator;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月25日
 */
public class MessageActivatorProcessor implements Input {

	private AddressingActivator addressingActivator;

	public MessageActivatorProcessor(AddressingActivator addressingActivator) {
		super();
		this.addressingActivator = addressingActivator;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.addressingActivator.activate(context);
		return null;
	}
}
