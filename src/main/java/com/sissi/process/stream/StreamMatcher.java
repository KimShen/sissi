package com.sissi.process.stream;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.stream.Stream;

/**
 * @author kim 2013-11-4
 */
public class StreamMatcher implements Matcher {

	@Override
	public Boolean match(Protocol protocol) {
		return Stream.class.isAssignableFrom(protocol.getClass());
	}
}
