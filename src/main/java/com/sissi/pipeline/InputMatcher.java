package com.sissi.pipeline;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月23日
 */
public interface InputMatcher {

	public boolean match(Protocol protocol);
}