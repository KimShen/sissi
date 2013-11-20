package com.sissi.context.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-19
 */
public class OfflineContextBuilder implements JIDContextBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		this.log.debug("JID:" + jid.asString() + " is offlined");
		return new OfflineContext(jid);
	}

	private class OfflineContext implements JIDContext {

		private JID jid;

		public OfflineContext(JID jid) {
			super();
			this.jid = jid;
		}

		@Override
		public JIDContext setAuth(Boolean canAccess) {
			return null;
		}

		@Override
		public Boolean isLogining() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Boolean isAuth() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public JIDContext setJid(JID jid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public JID getJid() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void write(Protocol protocol) {
			// TODO Auto-generated method stub

		}

		@Override
		public Boolean close() {
			return false;
		}
	}
}
