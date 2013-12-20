package com.sissi.pipeline.in.iq.disco;

import com.sissi.pipeline.in.ClassesMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.Info;
import com.sissi.protocol.iq.disco.Items;

/**
 * @author kim 2013年12月19日
 */
public class Disco2ServerMatcher extends ClassesMatcher {

	private String host;

	public Disco2ServerMatcher(String host) {
		super(Info.class, Items.class);
		this.host = host;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.isToServer(protocol);
	}

	private boolean isToServer(Protocol protocol) {
		return protocol.getTo() == null || protocol.getTo().equals(this.host);
	}
}
