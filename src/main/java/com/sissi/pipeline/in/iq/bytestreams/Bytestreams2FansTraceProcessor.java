package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.server.exchange.TracerContext;

/**
 * Bytestreams追踪
 * 
 * @author kim 2014年5月8日
 */
public class Bytestreams2FansTraceProcessor implements Input {

	private final TracerContext tracerContext;

	public Bytestreams2FansTraceProcessor(TracerContext tracerContext) {
		super();
		this.tracerContext = tracerContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Bytestreams bytestreams = protocol.cast(Bytestreams.class);
		return bytestreams.sid() ? this.trace(context, bytestreams) : true;
	}

	private boolean trace(JIDContext context, Bytestreams bytestreams) {
		return this.tracerContext.trace(bytestreams);
	}
}
