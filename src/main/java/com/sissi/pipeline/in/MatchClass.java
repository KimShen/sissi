package com.sissi.pipeline.in;

import com.sissi.pipeline.Input.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class MatchClass implements InputMatcher {

	private final Class<? extends Protocol> clazz;

	public MatchClass(Class<? extends Protocol> clazz) {
		super();
		this.clazz = clazz;
	}

	public Boolean match(Protocol protocol) {
		return this.clazz.isAssignableFrom(protocol.getClass());
	}
}
