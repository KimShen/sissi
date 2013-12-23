package com.sissi.context.impl;

import com.sissi.context.JIDContextParam;
import com.sissi.pipeline.Output;
import com.sissi.server.ServerTls;

/**
 * @author kim 2013年12月23日
 */
public class OnlineContextParam implements JIDContextParam {

	private final Output output;

	private final ServerTls serverTLS;

	public OnlineContextParam(Output output, ServerTls serverTLS) {
		super();
		this.output = output;
		this.serverTLS = serverTLS;
	}

	@Override
	public <T> T find(String key, Class<T> clazz) {
		switch (key) {
		case OnlineContextBuilder.KEY_OUTPUT:
			return clazz.cast(this.output);
		case OnlineContextBuilder.KEY_TLS:
			return clazz.cast(this.serverTLS);
		}
		return null;
	}
}