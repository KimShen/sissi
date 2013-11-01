package com.sisi.process.iq.fork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.process.iq.Forker;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.Protocol.Type;
import com.sisi.protocol.iq.Bind;

/**
 * @author kim 2013-10-29
 */
public class BindForker implements Forker {

	private final static String FORK_NAME = "bind";

	private Log log = LogFactory.getLog(this.getClass());

	private String host;

	public BindForker(String host) {
		super();
		this.host = host;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Bind bind = Bind.class.cast(protocol);
		String resource = bind.getResource().getText();
		bind.clear();
		context.jid().setHost(this.host);
		context.jid().setResource(resource);
		bind.setJid(context.jid().asString());
		this.log.debug("Bind: " + bind);
		return bind;
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return Bind.class.isAssignableFrom(protocol.getClass());
	}

	@Override
	public String fork() {
		return FORK_NAME;
	}

	@Override
	public Type type() {
		return Type.SET;
	}
}
