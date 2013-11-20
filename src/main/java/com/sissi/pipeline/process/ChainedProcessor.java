package com.sissi.pipeline.process;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-14
 */
public class ChainedProcessor implements ProcessPipeline {

	private List<ProcessPipeline> processors;

	public ChainedProcessor(List<ProcessPipeline> processors) {
		super();
		this.processors = processors;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		for (ProcessPipeline each : this.processors) {
			if (!each.process(context, protocol)) {
				return false;
			}
		}
		return true;
	}
}
