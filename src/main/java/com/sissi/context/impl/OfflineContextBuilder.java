package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.JIDContextBuilder;
import com.sissi.context.JIDContext.JIDContextParam;
import com.sissi.context.MyPresence;
import com.sissi.offline.StorageBox;
<<<<<<< HEAD
import com.sissi.protocol.Element;
=======
import com.sissi.protocol.Node;
import com.sissi.protocol.Protocol;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-19
 */
public class OfflineContextBuilder implements JIDContextBuilder {

	private final static Integer DEFAULT_PRIORITY = 0;

	private final StorageBox storageBox;

	public OfflineContextBuilder(StorageBox storageBox) {
		super();
		this.storageBox = storageBox;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		return new OfflineContext(jid);
	}

	private class OfflineContext implements JIDContext {

		private final JID jid;

		public OfflineContext(JID jid) {
			super();
			this.jid = jid;
		}

		public Long getIndex() {
			return null;
		}

		@Override
		public JIDContext setAuth(Boolean canAccess) {
			return this;
		}

		@Override
		public Boolean isAuth() {
			return false;
		}

		public Boolean isBinding() {
			return false;
		}

<<<<<<< HEAD
=======
		public Boolean isBinding() {
			return false;
		}

>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
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
<<<<<<< HEAD
		public void write(Element element) {
			OfflineContextBuilder.this.storageBox.store(element);
=======
		public void write(Node node) {
			if (Protocol.class.isAssignableFrom(node.getClass())) {
				OfflineContextBuilder.this.storageBox.push(Protocol.class.cast(node));
			}
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
		}

		@Override
		public Boolean close() {
			return false;
		}

		@Override
		public MyPresence getPresence() {
			return OfflineContextPresence.OFFLINE;
		}

		@Override
		public JIDContext setPriority(Integer priority) {
			return this;
		}

		@Override
		public Integer getPriority() {
			return OfflineContextBuilder.DEFAULT_PRIORITY;
		}
	}

	private static class OfflineContextPresence implements MyPresence {

		private final static MyPresence OFFLINE = new OfflineContextPresence();

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
		public MyPresence setTypeText(String type) {
			return this;
		}

		@Override
		public MyPresence setShowText(String show) {
			return this;
		}

		@Override
		public MyPresence setStatusText(String status) {
			return this;
		}

		public MyPresence clear() {
			return this;
		}
	}
}
