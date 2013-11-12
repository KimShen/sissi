package com.sissi.process.presence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.Context;
import com.sissi.process.Processor;
import com.sissi.process.ProcessorFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class PresenceForkProcessor implements Processor {
	
	private Log log = LogFactory.getLog(this.getClass());

	private ProcessorFinder finder;

	public PresenceForkProcessor(ProcessorFinder finder) {
		super();
		this.finder = finder;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		Processor processor = this.finder.find(protocol);
		this.log.debug("Protocol " + protocol.getClass() + " will use " + processor.getClass());
		processor.process(context, protocol);
	}
}
