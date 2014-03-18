package com.sissi.pipeline.in;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-14
 */
public class ChainedProcessor implements Input {

	private final boolean isNext;

	private final List<Input> processors;

	public ChainedProcessor(List<Input> processors) {
		this(Boolean.FALSE, processors);
	}

	public ChainedProcessor(boolean isNext, List<Input> processors) {
		super();
		this.isNext = isNext;
		this.processors = processors;
	}

	public void setTest(Input input) {
		this.processors.add(input);
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		for (Input each : this.processors) {
			if (!each.input(context, protocol)) {
				return false;
			}
		}
		return this.isNext;
	}
}
