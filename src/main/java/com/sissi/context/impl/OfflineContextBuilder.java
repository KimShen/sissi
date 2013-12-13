package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.JIDContextBuilder;
import com.sissi.context.JIDContext.JIDContextParam;
import com.sissi.context.JIDContext.Status;
import com.sissi.offline.DelayElementBox;
import com.sissi.protocol.Element;
import com.sissi.protocol.presence.Presence;

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

		@Override
		public String getTypeAsText() {
			return Presence.Type.UNAVAILABLE.toString();
		}

		@Override
		public String getShowAsText() {
			return null;
		}

		@Override
		public String getStatusAsText() {
			return null;
		}
		
		@Override
		public String getAvatorAsText() {
			return null;
		}

		@Override
		public Status asType(String type) {
			return this;
		}

		@Override
		public Status asShow(String show) {
			return this;
		}

		@Override
		public Status asStatus(String status) {
			return this;
		}
		
		public Status asAvator(String avator) {
			return this;
		}

		public Status clear() {
			return this;
		}
	}
}
