package com.sissi.pipeline.in;

import com.sissi.pipeline.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class ClassMatcher implements InputMatcher {

	private final Class<? extends Protocol> clazz;

	public ClassMatcher(Class<? extends Protocol> clazz) {
		super();
		this.clazz = clazz;
	}

	public boolean match(Protocol protocol) {
		return protocol.clazz(this.clazz);
	}
}
