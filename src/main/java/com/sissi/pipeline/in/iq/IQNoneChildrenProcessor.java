package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQNoneChildrenProcessor implements Input {

	private IQResultProcessor iqResultProcessor;

	public IQNoneChildrenProcessor(IQResultProcessor iqResultProcessor) {
		super();
		this.iqResultProcessor = iqResultProcessor;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return IQ.class.cast(protocol).listChildren().isEmpty() ? this.writeIOResult(context, protocol) : true;
	}

	private boolean writeIOResult(JIDContext context, Protocol protocol) {
		this.iqResultProcessor.input(context, protocol);
		return false;
	}
}
