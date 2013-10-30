package com.sisi.process.iq.fork;

import com.sisi.context.Context;
import com.sisi.process.iq.Forker;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.iq.Bind;

/**
 * @author kim 2013-10-29
 */
public class BindForker implements Forker {

	private final static String FORK_NAME = "bind";

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Bind resouce = Bind.class.cast(protocol);
		Bind bind = new Bind();
		context.jid().setHost("www.myaccount.com");
		context.jid().setResource(resouce.getResource().getText());
		bind.setJid(context.jid().asString());
		return bind;
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return Bind.class.isAssignableFrom(protocol.getClass());
	}

	@Override
	public String forkName() {
		return FORK_NAME;
	}
}
