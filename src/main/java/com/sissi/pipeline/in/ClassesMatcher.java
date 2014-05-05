package com.sissi.pipeline.in;

import java.util.Set;

import com.sissi.pipeline.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * 匹配Class簇
 * 
 * @author kim 2013年12月6日
 */
public class ClassesMatcher implements InputMatcher {

	private final Set<Class<? extends Protocol>> clazzes;

	public ClassesMatcher(Set<Class<? extends Protocol>> protocols) {
		super();
		this.clazzes = protocols;
	}

	@Override
	public boolean match(Protocol protocol) {
		return protocol.clazz(this.clazzes);
	}
}
