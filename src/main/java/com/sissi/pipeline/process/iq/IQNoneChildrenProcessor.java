package com.sissi.pipeline.process.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQNoneChildrenProcessor implements ProcessPipeline {

	private ProcessPipeline iqResultProcessor;

	public IQNoneChildrenProcessor(ProcessPipeline ioResultProcessor) {
		super();
		this.iqResultProcessor = ioResultProcessor;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		IQ iq = IQ.class.cast(protocol);
		return iq.listChildren().isEmpty() ? this.writeIOResult(context, protocol) : true;
	}

	private boolean writeIOResult(JIDContext context, Protocol protocol) {
		this.iqResultProcessor.process(context, protocol);
		return false;
	}
}
