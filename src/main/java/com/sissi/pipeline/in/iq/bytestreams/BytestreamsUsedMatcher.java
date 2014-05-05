package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;

/**
 * <query xmlns='http://jabber.org/protocol/bytestreams'><streamhost-used jid='streamhostproxy.example.net'/></query>
 * 
 * @author kim 2013年12月24日
 */
public class BytestreamsUsedMatcher extends ClassMatcher {

	public BytestreamsUsedMatcher() {
		super(Bytestreams.class);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Bytestreams.class).used();
	}
}
