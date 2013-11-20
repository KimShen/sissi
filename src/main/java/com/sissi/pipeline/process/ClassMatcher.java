package com.sissi.pipeline.process;

import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class ClassMatcher implements ProcessPipelineMatcher {

	private Class<? extends Protocol> clazz;

	public ClassMatcher(Class<? extends Protocol> clazz) {
		super();
		this.clazz = clazz;
	}

	public Boolean match(Protocol protocol) {
		return this.clazz.isAssignableFrom(protocol.getClass());
	}
}
