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
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 离线JIDContext
 * 
 * @author kim 2013-11-19
 */
public class OfflineContextBuilder implements JIDContextBuilder {

	private final SocketAddress address = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0);

	private final int priority = -1;

	private final VCardContext vCardContext;

	private final String domain;

	private final Output output;

	private final String lang;

	/**
	 * @param lang 默认语言
	 * @param domain 默认域
	 * @param output
	 * @param vCardContext
	 * @throws Exception
	 */
	public OfflineContextBuilder(String lang, String domain, Output output, VCardContext vCardContext) throws Exception {
		super();
		this.lang = lang;
		this.domain = domain;
		this.output = output;
		this.vCardContext = vCardContext;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		return new OfflineContext(jid);
	}

	private class OfflineContext implements JIDContext {

		private final JID jid;

		private final Status status;

		/**
		 * 使用VCard加载离线签名及最后活跃时间
		 * 
		 * @param jid
		 */
		public OfflineContext(JID jid) {
			this.status = new OfflineStatus(new OfflineStatusClauses(OfflineContextBuilder.this.vCardContext.get(jid, VCardContext.FIELD_SIGNATURE).getValue()));
			this.jid = jid;
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

		public JIDContext online() {
			return this;
		}

		public boolean onlined() {
			return true;
		}

		public JIDContext offline() {
			return this;
		}

		public long idle() {
			String idle = OfflineContextBuilder.this.vCardContext.get(this.jid(), VCardContext.FIELD_LOGOUT).getValue();
			return idle == null ? 0L : Long.valueOf(idle);
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

		public JIDContext ping(int ping) {
			return this;
		}

		public JIDContext pong(Element element) {
			return this;
		}

		@Override
		public JIDContext write(Element element) {
			OfflineContextBuilder.this.output.output(this, element.setTo(this.jid.asString()));
			return this;
		}

		public JIDContext write(Element element, boolean force) {
			return this.write(element);
		}

		public JIDContext write(Element element, boolean force, boolean bare) {
			return this.write(element);
		}

		public JIDContext write(Collection<Element> elements) {
			for (Element element : elements) {
				this.write(element);
			}
			return this;
		}

		public JIDContext write(Collection<Element> elements, boolean force) {
			return this.write(elements);
		}

		public JIDContext write(Collection<Element> elements, boolean force, boolean bare) {
			return this.write(elements);
		}
	}

	private class OfflineStatusClauses implements StatusClauses {

		private final String signature;

		private OfflineStatusClauses(String signature) {
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
