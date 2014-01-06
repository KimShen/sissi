package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.iq.IQProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月7日
 */
public class SessionProcessor implements Input {

	private final IQProcessor iqProcessor;

	public SessionProcessor(IQProcessor iqProcessor) {
		super();
		this.iqProcessor = iqProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isBinding() ? true : this.iqProcessor.input(context, protocol.getParent());
	}
}
