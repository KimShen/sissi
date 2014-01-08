package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.InputFinder;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQForkProcessor implements Input {

	private final InputFinder finder;

	public IQForkProcessor(InputFinder finder) {
		super();
		this.finder = finder;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (Protocol sub : IQ.class.cast(protocol).listChildren()) {
			return this.finder.find(sub).input(context, sub);
		}
		return true;
	}
}
