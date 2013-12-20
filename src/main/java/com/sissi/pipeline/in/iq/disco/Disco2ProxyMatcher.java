package com.sissi.pipeline.in.iq.disco;

import com.sissi.pipeline.in.ClassesMatcher;
import com.sissi.pipeline.in.iq.bytestreams.BytestreamsProxy;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.Info;
import com.sissi.protocol.iq.disco.Items;

/**
 * @author kim 2013年12月19日
 */
public class Disco2ProxyMatcher extends ClassesMatcher {

	private BytestreamsProxy bytestreamsProxy;

	public Disco2ProxyMatcher(BytestreamsProxy bytestreamsProxy) {
		super(Info.class, Items.class);
		this.bytestreamsProxy = bytestreamsProxy;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.isToProxy(protocol);
	}

	private boolean isToProxy(Protocol protocol) {
		return protocol.getTo() != null && protocol.getTo().equals(this.bytestreamsProxy.getJid());
	}
}
