package com.sissi.pipeline.in;

import java.util.ArrayList;
import java.util.List;

import com.sissi.pipeline.Input.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月6日
 */
public class ClassesMatcher implements InputMatcher {

	private final List<Class<? extends Protocol>> clazzes;

	@SafeVarargs
	public ClassesMatcher(Class<? extends Protocol>... protocol) {
		List<Class<? extends Protocol>> protocols = new ArrayList<Class<? extends Protocol>>();
		for (Class<? extends Protocol> each : protocol) {
			protocols.add(each);
		}
		this.clazzes = protocols;
	}

	public ClassesMatcher(List<Class<? extends Protocol>> clazzes) {
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
