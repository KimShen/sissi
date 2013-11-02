package com.sissi.context.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.Context;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
public class UserContext implements Context {

	private final static Log LOG = LogFactory.getLog(UserContext.class);

	private enum AuthState {

		ACCESS, REFUSE;

		public static AuthState valueOf(Boolean canAccess) {
			return canAccess ? ACCESS : REFUSE;
		}
	}

	private Writeable writeable;

	private AuthState state;

	private JID jid;

	public UserContext(Writeable writeable) {
		super();
		this.writeable = writeable;
		this.state = AuthState.REFUSE;
	}

	@Override
	public Boolean access() {
		boolean canAccess = (this.state == AuthState.ACCESS);
		LOG.info("JID: " + (jid != null ? jid.asString() : "N/A") + " can access -> " + canAccess);
		return canAccess;
	}

	@Override
	public Boolean access(Boolean canAccess) {
		this.state = AuthState.valueOf(canAccess);
		return this.access();
	}

	public JID jid(JID jid) {
		this.jid = jid;
		return this.jid();
	}

	public JID jid() {
		LOG.info("JID: " + (jid != null ? jid.asString() : "N/A"));
		return this.jid;
	}

	public void write(Protocol protocol) {
		LOG.debug("Write Protocol: " + protocol);
		this.writeable.writeAndFlush(protocol);
	}
}
