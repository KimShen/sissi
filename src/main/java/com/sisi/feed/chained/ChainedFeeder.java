package com.sisi.feed.chained;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.feed.Feeder;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class ChainedFeeder implements Feeder {

	private Log log = LogFactory.getLog(this.getClass());

	private Context context;

	private List<Processor> processors;

	public ChainedFeeder(Context context, List<Processor> processors) {
		super();
		this.processors = processors;
		this.context = context;
	}

	@Override
	public void feed(Protocol protocol) {
		for (Processor processor : processors) {
			this.doEachProcessor(protocol, processor);
		}
	}

	private void doEachProcessor(Protocol protocol, Processor processor) {
		if (processor.isSupport(protocol)) {
			this.log.debug("Protocol wouble be processed by " + processor.getClass());
			Protocol afterProcess = processor.process(this.context, protocol);
			this.log.debug("Protocol from " + processor.getClass() + " is " + afterProcess);
			if (afterProcess != null) {
				this.context.write(afterProcess);
			}
		}
	}
}
