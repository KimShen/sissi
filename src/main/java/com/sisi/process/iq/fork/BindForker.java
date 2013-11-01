package com.sisi.process.iq.fork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.addressing.Addressing;
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

	private Addressing addressing;

	public BindForker(String host, Addressing addressing) {
		super();
		this.host = host;
		this.addressing = addressing;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Bind bind = Bind.class.cast(protocol);
		bind.clear();
		context.jid().setHost(this.host);
		if (bind.hasResource()) {
			context.jid().setResource(bind.getResource().getText());
		}
		bind.setJid(context.jid().asString());
		this.addressing.join(context);
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
