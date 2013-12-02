package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQForkProcessor implements Input {

	private InputFinder finder;

	private IQResultProcessor iqResultProcessor;

	public IQForkProcessor(InputFinder finder, IQResultProcessor iqResultProcessor) {
		super();
		this.finder = finder;
		this.iqResultProcessor = iqResultProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (Protocol sub : IQ.class.cast(protocol).listChildren()) {
			return this.finder.find(sub).input(context, sub);
		}
		return this.iqResultProcessor.input(context, protocol);
	}
}
