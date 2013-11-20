package com.sissi.pipeline.process.presence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.pipeline.ProcessPipelineFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class PresenceForkProcessor implements ProcessPipeline {
	
	private Log log = LogFactory.getLog(this.getClass());

	private ProcessPipelineFinder finder;

	public PresenceForkProcessor(ProcessPipelineFinder finder) {
		super();
		this.finder = finder;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		ProcessPipeline processor = this.finder.find(protocol);
		this.log.debug("Protocol " + protocol.getClass() + " will use " + processor.getClass());
		processor.process(context, protocol);
		return false;
	}
}
