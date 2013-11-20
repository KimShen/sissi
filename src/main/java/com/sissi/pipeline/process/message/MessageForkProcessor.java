package com.sissi.pipeline.process.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.pipeline.ProcessPipelineFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class MessageForkProcessor implements ProcessPipeline {

	private ProcessPipelineFinder finder;

	public MessageForkProcessor(ProcessPipelineFinder finder) {
		super();
		this.finder = finder;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		this.finder.find(protocol).process(context, protocol);
		return false;
	}
}
