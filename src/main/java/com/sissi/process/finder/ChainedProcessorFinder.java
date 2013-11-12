package com.sissi.process.finder;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.process.Processor;
import com.sissi.process.ProcessorFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class ChainedProcessorFinder implements ProcessorFinder {

	private Log log = LogFactory.getLog(this.getClass());

	private List<MatchProcessor> processors;

	public ChainedProcessorFinder(List<MatchProcessor> processors) {
		super();
		this.processors = processors;
	}

	@Override
	public Processor find(Protocol protocol) {
		for (MatchProcessor matchProcessor : this.processors) {
			if (matchProcessor.getMatcher().match(protocol)) {
				this.log.debug("Protocol " + protocol.getClass() + " will use " + matchProcessor.getClass());
				return matchProcessor.getProcessor();
			}
		}
		this.log.warn("None Processor can process " + protocol.getClass());
		return null;
	}
}
