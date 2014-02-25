package com.sissi.pipeline.in;

import java.util.HashSet;
import java.util.Set;

import com.sissi.pipeline.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月6日
 */
public class ClassesMatcher implements InputMatcher {

	private final Set<Class<? extends Protocol>> clazzes = new HashSet<Class<? extends Protocol>>();

	public ClassesMatcher(Set<Class<? extends Protocol>> protocol) {
		super();
		this.clazzes.addAll(protocol);
	}

	@Override
	public boolean match(Protocol protocol) {
		return protocol.clazz(this.clazzes);
	}
}
