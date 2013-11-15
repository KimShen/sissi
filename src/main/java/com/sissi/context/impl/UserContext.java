package com.sissi.context.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.OutputPipeline;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
public class UserContext implements JIDContext {

	private Log log = LogFactory.getLog(this.getClass());

	private enum AuthState {

		ACCESS, REFUSE;

		public static AuthState valueOf(Boolean canAccess) {
			return canAccess ? ACCESS : REFUSE;
		}
	}

	private JID jid;

	private AuthState state;

	private OutputPipeline outputPipeline;

	public UserContext(OutputPipeline outputPipeline) {
		super();
		this.outputPipeline = outputPipeline;
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
		this.log.info("JID " + (this.jid != null ? this.jid.asStringWithBare() : "N/A") + " start write ...");
		this.outputPipeline.write(protocol);
	}
}
