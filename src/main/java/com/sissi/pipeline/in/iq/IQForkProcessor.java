package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQForkProcessor implements Input {

	private final InputFinder finder;

	private final IQTypeProcessor iqTypeProcessor;

	public IQForkProcessor(InputFinder finder, IQTypeProcessor iqTypeProcessor) {
		super();
		this.finder = finder;
		this.iqTypeProcessor = iqTypeProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (Protocol sub : IQ.class.cast(protocol).listChildren()) {
			return this.finder.find(sub).input(context, sub);
		}
		return this.iqTypeProcessor.input(context, protocol);
	}
}
