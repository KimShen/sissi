package com.sissi.context.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.Context;
import com.sissi.context.JID;
import com.sissi.context.Writeable;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
public class UserContext implements Context {

	private Log log = LogFactory.getLog(this.getClass());

	private enum AuthState {

		ACCESS, REFUSE;

		public static AuthState valueOf(Boolean canAccess) {
			return canAccess ? ACCESS : REFUSE;
		}
	}

	private JID jid;

	private AuthState state;

	private Writeable writeable;

	public UserContext(Writeable writeable) {
		super();
		this.writeable = writeable;
		this.state = AuthState.REFUSE;
	}

	@Override
	public Boolean access() {
		return this.state == AuthState.ACCESS;
	}

	@Override
	public Boolean access(Boolean canAccess) {
		this.state = AuthState.valueOf(canAccess);
		return this.access();
	}

	public Boolean online() {
		return this.jid != null;
	}

	public JID jid(JID jid) {
		this.jid = jid;
		return this.jid();
	}

	public JID jid() {
		return this.jid;
	}

	public void write(Protocol protocol) {
		this.log.info("JID " + (this.jid != null ? this.jid.asStringWithNaked() : "N/A") + " start write ...");
		this.writeable.writeAndFlush(protocol);
	}
}
