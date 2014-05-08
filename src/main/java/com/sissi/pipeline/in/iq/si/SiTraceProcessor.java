package com.sissi.pipeline.in.iq.si;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.si.Si;
import com.sissi.server.exchange.TracerContext;

/**
 * SI追踪
 * 
 * @author kim 2014年5月8日
 */
public class SiTraceProcessor implements Input {

	private final TracerContext tracerContext;

	public SiTraceProcessor(TracerContext tracerContext) {
		super();
		this.tracerContext = tracerContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Si si = protocol.cast(Si.class);
		return si.file() ? this.trace(context, si) : true;
	}

	private boolean trace(JIDContext context, Si si) {
		return this.tracerContext.trace(si);
	}
}
