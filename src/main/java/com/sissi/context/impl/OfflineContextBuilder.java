package com.sissi.context.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.Status;
import com.sissi.context.StatusClauses;
import com.sissi.offline.DelayElementBox;
import com.sissi.protocol.Element;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-11-19
 */
public class OfflineContextBuilder implements JIDContextBuilder {

	private final Integer priority = 0;

	private final Status status = new OfflineStatus();

	private final JIDContext context = new OfflineContext();

	private final SocketAddress address = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);

	private final String lang;
	
	private final String domain;
	
	private final DelayElementBox delayElementBox;

	public OfflineContextBuilder(String lang, String domain, DelayElementBox delayElementBox) throws Exception {
		super();
		this.lang = lang;
		this.domain = domain;
		this.delayElementBox = delayElementBox;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		return this.context;
	}

	private class OfflineContext implements JIDContext {

		private final JID jid = OfflineJID.OFFLINE;

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

		public Boolean isAuthRetry() {
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
		public Status getStatus() {
			return OfflineContextBuilder.this.status;
		}

		@Override
		public JIDContext setPriority(Integer priority) {
			return this;
		}

		@Override
		public Integer getPriority() {
			return OfflineContextBuilder.this.priority;
		}

		public JIDContext setDomain(String domain) {
			return this;
		}

		public JIDContext setLang(String lang) {
			return this;
		}

		public String getLang() {
			return OfflineContextBuilder.this.lang;
		}

		public String getDomain() {
			return OfflineContextBuilder.this.domain;
		}

		public SocketAddress getAddress() {
			return OfflineContextBuilder.this.address;
		}

		@Override
		public Boolean setTls() {
			return false;
		}

		public Boolean isTls() {
			return false;
		}

		public Boolean closePrepare() {
			return false;
		}

		public Boolean closeTimeout() {
			return false;
		}

		@Override
		public Boolean close() {
			return false;
		}

		public JIDContext reset() {
			return this;
		}

		public JIDContext ping() {
			return this;
		}

		public JIDContext pong(Element element) {
			return this;
		}

		@Override
		public JIDContext write(Element element) {
			OfflineContextBuilder.this.delayElementBox.push(element);
			return this;
		}

		public JIDContext write(Collection<Element> elements) {
			for (Element element : elements) {
				this.write(element);
			}
			return this;
		}
	}

	private class OfflineStatus implements Status {

		private final StatusClauses empty = new EmptyClauses();

		private OfflineStatus() {

		}

		public Status clear() {
			return this;
		}

		@Override
		public Status setStatus(StatusClauses clauses) {
			return this;
		}

		@Override
		public StatusClauses getStatusClauses() {
			return this.empty;
		}
	}

	private class EmptyClauses implements StatusClauses {

		private EmptyClauses() {

		}

		@Override
		public String find(String key) {
			switch (key) {
			case StatusClauses.KEY_TYPE:
				return PresenceType.UNAVAILABLE.toString();
			}
			return null;
		}
	}
}
