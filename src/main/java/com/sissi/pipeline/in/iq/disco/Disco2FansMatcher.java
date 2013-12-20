package com.sissi.pipeline.in.iq.disco;

import com.sissi.pipeline.in.ClassesMatcher;
import com.sissi.pipeline.in.iq.bytestreams.BytestreamsProxy;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.Info;
import com.sissi.protocol.iq.disco.Items;

/**
 * @author kim 2013年12月19日
 */
public class Disco2FansMatcher extends ClassesMatcher {

	private String host;

	private BytestreamsProxy bytestreamsProxy;

	public Disco2FansMatcher(String host, BytestreamsProxy bytestreamsProxy) {
		super(Info.class, Items.class);
		this.host = host;
		this.bytestreamsProxy = bytestreamsProxy;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.isToFans(protocol);
	}

	private boolean isToFans(Protocol protocol) {
		return protocol.getTo() != null && !protocol.getTo().equals(this.host) && !protocol.getTo().equals(this.bytestreamsProxy.getJid());
	}
}
