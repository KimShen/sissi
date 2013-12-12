package com.sissi.pipeline.in;

import java.util.List;

import com.sissi.pipeline.Input.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月6日
 */
public class ClassesMatch implements InputMatcher {

	private final List<Class<? extends Protocol>> clazzes;

	public ClassesMatch(List<Class<? extends Protocol>> clazzes) {
		super();
		this.clazzes = clazzes;
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
