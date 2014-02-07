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
import com.sissi.pipeline.Output;
import com.sissi.protocol.Element;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2013-11-19
 */
public class OfflineContextBuilder implements JIDContextBuilder {

	private final int priority = 0;

	private final JIDContext context = new OfflineContext();

	private final SocketAddress address = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);

	private final VCardContext vCardContext;

	private final String lang;

	private final String domain;

	private final Output output;

	public OfflineContextBuilder(String lang, String domain, Output output) throws Exception {
		super();
		this.lang = lang;
		this.domain = domain;
		this.output = output;
		this.vCardContext = null;
	}

	public OfflineContextBuilder(String lang, String domain, Output output, VCardContext vCardContext) throws Exception {
		super();
		this.lang = lang;
		this.domain = domain;
		this.output = output;
		this.vCardContext = vCardContext;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		return this.vCardContext != null ? new OfflineContext(new SignatureClauses(this.vCardContext.get(jid, VCardContext.SIGNATURE).getValue())) : this.context;
	}

	private class OfflineContext implements JIDContext {

		private final JID jid = OfflineJID.JID;

		private final Status status;

		public OfflineContext() {
			super();
			this.status = OfflineStatus.STATUS;
		}

		public OfflineContext(StatusClauses statusClauses) {
			super();
			this.status = new OfflineStatus(statusClauses);
		}

		public long index() {
			return -1;
		}

		@Override
		public JIDContext auth(boolean canAccess) {
			return this;
		}

		@Override
		public boolean auth() {
			return false;
		}

		public boolean authRetry() {
			return false;
		}

		public JIDContext bind() {
			return this;
		}

		public boolean binding() {
			return false;
		}

		@Override
		public JIDContext jid(JID jid) {
			return this;
		}

		@Override
		public JID jid() {
			return this.jid;
		}

		@Override
		public Status status() {
			return this.status;
		}

		@Override
		public JIDContext priority(int priority) {
			return this;
		}

		@Override
		public int priority() {
			return OfflineContextBuilder.this.priority;
		}

		public JIDContext domain(String domain) {
			return this;
		}

		public JIDContext lang(String lang) {
			return this;
		}

		public String lang() {
			return OfflineContextBuilder.this.lang;
		}

		public String domain() {
			return OfflineContextBuilder.this.domain;
		}

		public SocketAddress address() {
			return OfflineContextBuilder.this.address;
		}

		@Override
		public boolean encrypt() {
			return false;
		}

		public boolean encrypted() {
			return false;
		}

		public JIDContext present() {
			return this;
		}

		public boolean presented() {
			return false;
		}

		public boolean closePrepare() {
			return false;
		}

		public boolean closeTimeout() {
			return false;
		}

		@Override
		public boolean close() {
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
			OfflineContextBuilder.this.output.output(this, element);
			return this;
		}

		public JIDContext write(Collection<Element> elements) {
			for (Element element : elements) {
				this.write(element);
			}
			return this;
		}
	}

	private class SignatureClauses implements StatusClauses {

		private final String signature;

		private SignatureClauses(String signature) {
			this.signature = signature;
		}

		@Override
		public String find(String key) {
			switch (key) {
			case StatusClauses.KEY_TYPE:
				return PresenceType.UNAVAILABLE.toString();
			case StatusClauses.KEY_STATUS:
				return this.signature;
			}
			return null;
		}
	}
}
