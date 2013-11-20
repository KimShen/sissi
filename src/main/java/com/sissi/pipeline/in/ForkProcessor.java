package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.InputCondition.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class ForkProcessor implements Input {

	private InputFinder finder;

	public ForkProcessor(InputFinder finder) {
		super();
		this.finder = finder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Input subProcessor = this.finder.find(protocol);
		if (!subProcessor.input(context, protocol)) {
			return false;
		}
		return false;
	}
}
