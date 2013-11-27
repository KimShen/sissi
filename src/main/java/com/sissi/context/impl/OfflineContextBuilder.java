package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.JIDContextPresence;
import com.sissi.offline.StorageBox;
import com.sissi.protocol.Node;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-19
 */
public class OfflineContextBuilder implements JIDContextBuilder {

	private StorageBox storageBox;

	public OfflineContextBuilder(StorageBox storageBox) {
		super();
		this.storageBox = storageBox;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
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
			return this;
		}

		@Override
		public Boolean isLogining() {
			return false;
		}

		@Override
		public Boolean isAuth() {
			return false;
		}

		public Boolean isBinding() {
			return false;
		}

		public JIDContext setBinding(Boolean isBinding) {
			return this;
		}

		@Override
		public JIDContext setJid(JID jid) {
			return this;
		}

		@Override
		public JID getJid() {
			return this.jid;
		}

		@Override
		public void write(Node node) {
			if (Protocol.class.isAssignableFrom(node.getClass())) {
				OfflineContextBuilder.this.storageBox.push(Protocol.class.cast(node));
			}
		}

		@Override
		public Boolean close() {
			return false;
		}

		@Override
		public JIDContextPresence getPresence() {
			return OfflineContextPresence.OFFLINE;
		}
	}

	private static class OfflineContextPresence implements JIDContextPresence {

		private final static JIDContextPresence OFFLINE = new OfflineContextPresence();

		private OfflineContextPresence() {

		}

		@Override
		public String getTypeText() {
			return Presence.Type.UNAVAILABLE.toString();
		}

		@Override
		public String getShowText() {
			return null;
		}

		@Override
		public String getStatusText() {
			return null;
		}

		@Override
		public JIDContextPresence setTypeText(String type) {
			return this;
		}

		@Override
		public JIDContextPresence setShowText(String show) {
			return this;
		}

		@Override
		public JIDContextPresence setStatusText(String status) {
			return this;
		}

		public JIDContextPresence clear() {
			return this;
		}
	}
}
