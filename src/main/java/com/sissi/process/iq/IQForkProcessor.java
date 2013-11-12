package com.sissi.process.iq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.Context;
import com.sissi.process.Processor;
import com.sissi.process.ProcessorFinder;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQForkProcessor implements Processor {

	private Log log = LogFactory.getLog(this.getClass());

	private ProcessorFinder finder;

	private Processor ioResultProcessor;

	public IQForkProcessor(ProcessorFinder finder, Processor ioResultProcessor) {
		super();
		this.finder = finder;
		this.ioResultProcessor = ioResultProcessor;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		IQ iq = IQ.class.cast(protocol);
		if (this.noneChildToProcess(iq)) {
			this.writeIOResult(context, protocol);
			return;
		}
		this.doFork(context, iq);
	}

	private void writeIOResult(Context context, Protocol protocol) {
		this.ioResultProcessor.process(context, protocol);
		return;
	}

	private boolean noneChildToProcess(IQ iq) {
		return iq.listChildren().isEmpty();
	}

	private void doFork(Context context, IQ request) {
		for (Protocol sub : request.listChildren()) {
			Processor subProcessor = this.finder.find(sub);
			this.log.debug("Protocol " + sub.getClass() + " will use " + subProcessor.getClass());
			subProcessor.process(context, sub);
		}
	}
}
