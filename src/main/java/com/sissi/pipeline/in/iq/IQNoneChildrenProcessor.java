package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQNoneChildrenProcessor implements Input {

	private final IQTypeProcessor iqTypeProcessor;

	public IQNoneChildrenProcessor(IQTypeProcessor iqTypeProcessor) {
		super();
		this.iqTypeProcessor = iqTypeProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return IQ.class.cast(protocol).listChildren().isEmpty() ? this.writeIOResult(context, protocol) : true;
	}

	private boolean writeIOResult(JIDContext context, Protocol protocol) {
		return this.iqTypeProcessor.input(context, protocol);
	}
}
