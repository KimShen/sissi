package com.sissi.pipeline.in;

import java.util.ArrayList;
import java.util.List;

import com.sissi.pipeline.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月6日
 */
public class ClassesMatcher implements InputMatcher {

	private final List<Class<? extends Protocol>> clazzes = new ArrayList<Class<? extends Protocol>>();

	public ClassesMatcher(List<Class<? extends Protocol>> protocol) {
		super();
		this.clazzes.addAll(protocol);
	}

	@Override
	public boolean match(Protocol protocol) {
		return protocol.clazz(this.clazzes);
	}
}
