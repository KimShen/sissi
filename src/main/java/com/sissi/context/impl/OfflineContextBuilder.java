package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.JIDContextBuilder;
import com.sissi.context.JIDContext.JIDContextParam;
import com.sissi.context.JIDContext.Status;
import com.sissi.context.JIDContext.StatusClauses;
import com.sissi.offline.DelayElementBox;
import com.sissi.protocol.Element;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-19
 */
public class OfflineContextBuilder implements JIDContextBuilder {

	private final static Integer DEFAULT_PRIORITY = 0;

	private final DelayElementBox delayElementBox;

	public OfflineContextBuilder(DelayElementBox delayElementBox) {
		super();
		this.delayElementBox = delayElementBox;
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
		public JIDContext write(Element element) {
			OfflineContextBuilder.this.delayElementBox.add(element);
			return this;
		}

		@Override
		public JIDContext setStarttls() {
			return this;
		}

		@Override
		public Boolean isStarttls() {
			return false;
		}
		
		@Override
		public Boolean close() {
			return false;
		}

		@Override
		public Status getStatus() {
			return OfflineStatus.OFFLINE;
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

	private static class OfflineStatus implements Status {

		private final static Status OFFLINE = new OfflineStatus();

		private OfflineStatus() {

		}

		public Status close() {
			return this;
		}

		@Override
		public Status setStatus(String type, String show, String status, String avator) {
			return this;
		}

		@Override
		public StatusClauses getStatus() {
			return EmptyClauses.EMPTY;
		}
	}

	private static class EmptyClauses implements StatusClauses {

		private final static StatusClauses EMPTY = new EmptyClauses();

		private EmptyClauses() {

		}

		@Override
		public String find(String key) {
			switch (key) {
			case StatusClauses.KEY_TYPE:
				return Type.UNAVAILABLE.toString();
			}
			return null;
		}
	}
}
