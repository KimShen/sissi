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

	@SafeVarargs
	public ClassesMatcher(Class<? extends Protocol>... protocol) {
		for (Class<? extends Protocol> each : protocol) {
			clazzes.add(each);
		}
	}

	public ClassesMatcher(List<Class<? extends Protocol>> clazzes) {
		super();
		this.clazzes.addAll(clazzes);
	}

	@Override
	public Boolean match(Protocol protocol) {
		for (Class<? extends Protocol> each : this.clazzes) {
			if (each == protocol.getClass()) {
				return true;
			}
		}
		return false;
	}
}
