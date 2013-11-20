package com.sissi.pipeline.process;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.pipeline.ProcessPipeline;
import com.sissi.pipeline.ProcessPipelineFinder;
import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class ChainedFinder implements ProcessPipelineFinder {

	private Log log = LogFactory.getLog(this.getClass());

	private List<Condition> processors;

	public ChainedFinder(List<Condition> processors) {
		super();
		this.processors = processors;
	}

	@Override
	public ProcessPipeline find(Protocol protocol) {
		for (Condition matchProcessor : this.processors) {
			if (matchProcessor.getMatcher().match(protocol)) {
				this.log.debug("Protocol " + protocol.getClass() + " will use " + matchProcessor.getClass());
				return matchProcessor.getProcessor();
			}
		}
		this.log.warn("None Processor can process " + protocol.getClass());
		return null;
	}

	public static class Condition {

		private ProcessPipelineMatcher matcher;

		private ProcessPipeline processor;

		public Condition(ProcessPipelineMatcher matcher, ProcessPipeline processor) {
			super();
			this.matcher = matcher;
			this.processor = processor;
		}

		public ProcessPipelineMatcher getMatcher() {
			return matcher;
		}

		public ProcessPipeline getProcessor() {
			return processor;
		}
	}
}
