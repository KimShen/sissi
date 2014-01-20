package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;

/**
 * @author kim 2013年12月24日
 */
public class BytestreamsActivateMatcher extends ClassMatcher {

	public BytestreamsActivateMatcher() {
		super(Bytestreams.class);
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) && Bytestreams.class.cast(protocol).isActivate();
	}
}
