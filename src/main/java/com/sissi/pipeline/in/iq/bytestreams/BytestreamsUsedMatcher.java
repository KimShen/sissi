package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;

/**
 * @author kim 2013年12月19日
 */
public class BytestreamsUsedMatcher extends ClassMatcher {

	public BytestreamsUsedMatcher() {
		super(Bytestreams.class);
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) && Bytestreams.class.cast(protocol).isUsed();
	}
}
