package com.sissi.pipeline.in;

import java.util.ArrayList;
import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-14
 */
public class ChainedProcessor implements Input {

	private Boolean doNext = Boolean.FALSE;

	private List<Input> processors;

	public ChainedProcessor() {
		super();
		this.processors = new ArrayList<Input>();
	}

	public ChainedProcessor(List<Input> processors) {
		super();
		this.processors = processors;
	}

	public ChainedProcessor(Boolean doNext, List<Input> processors) {
		super();
		this.doNext = doNext;
		this.processors = processors;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (Input each : this.processors) {
			if (!each.input(context, protocol)) {
				return false;
			}
		}
		return this.doNext;
	}
}
