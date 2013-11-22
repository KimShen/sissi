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
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.finder.find(protocol).input(context, protocol);
	}
}
