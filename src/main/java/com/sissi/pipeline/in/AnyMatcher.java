package com.sissi.pipeline.in;

import com.sissi.pipeline.Input.InputMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class AnyMatcher implements InputMatcher {

	@Override
	public Boolean match(Protocol protocol) {
		return true;
	}
}
