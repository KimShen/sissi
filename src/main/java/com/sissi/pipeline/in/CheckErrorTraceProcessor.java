package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.server.exchange.TracerContext;

/**
 * 文件传输取消校验
 * 
 * @author kim 2014年5月8日
 */
public class CheckErrorTraceProcessor implements Input {

	private final Input proxy;

	private final TracerContext tracerContext;

	public CheckErrorTraceProcessor(Input proxy, TracerContext tracerContext) {
		super();
		this.proxy = proxy;
		this.tracerContext = tracerContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.tracerContext.trace(protocol.parent().getId()) ? this.proxy.input(context, protocol) : true;
	}
}
