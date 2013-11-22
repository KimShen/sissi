package com.sissi.context.impl;

import com.sissi.context.JIDContextParam;
import com.sissi.pipeline.Output;

/**
 * @author kim 2013-11-19
 */
public class UserContextParam implements JIDContextParam {

	private Output output;

	public UserContextParam(Output output) {
		super();
		this.output = output;
	}

	@Override
	public <T> T find(String key, Class<T> clazz) {
		return OnlineContextBuilder.KEY_OUTPUT.equals(key) ? clazz.cast(output) : null;
	}
}
