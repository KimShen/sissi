package com.sissi.pipeline.process.iq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.pipeline.ProcessPipelineFinder;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQForkProcessor implements ProcessPipeline {

	private Log log = LogFactory.getLog(this.getClass());

	private ProcessPipelineFinder finder;

	public IQForkProcessor(ProcessPipelineFinder finder) {
		super();
		this.finder = finder;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		return this.doFork(context, IQ.class.cast(protocol));
	}

	private boolean doFork(JIDContext context, IQ request) {
		for (Protocol sub : request.listChildren()) {
			ProcessPipeline subProcessor = this.finder.find(sub);
			this.log.debug("Protocol " + sub.getClass() + " will use " + subProcessor.getClass());
			if (!subProcessor.process(context, sub)) {
				return false;
			}
		}
		return true;
	}
}
