package com.sissi.process.finder;

import com.sissi.process.Matcher;
import com.sissi.process.Processor;

/**
 * @author kim 2013-11-6
 */
public class MatchProcessor {

	private Matcher matcher;

	private Processor processor;

	public MatchProcessor(Matcher matcher, Processor processor) {
		super();
		this.matcher = matcher;
		this.processor = processor;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public Processor getProcessor() {
		return processor;
	}
}