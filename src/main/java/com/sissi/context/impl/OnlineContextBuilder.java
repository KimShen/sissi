package com.sissi.context.impl;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.Status;
import com.sissi.context.StatusBuilder;
import com.sissi.field.impl.BeanField;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Element;
import com.sissi.server.ha.Keepalive;
import com.sissi.server.tls.StartTls;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 在线JIDContext
 * 
 * @author kim 2013-11-19
 */
public class OnlineContextBuilder implements JIDContextBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	/**
	 * Identify
	 */
	private final AtomicLong indexes = new AtomicLong();

	private final int priority = 0;

	private final int pong = -1;

	private final StatusBuilder statusBuilder;

	private final VCardContext vCardContext;

	private final Keepalive keepalive;

	private final String lang;

	private final Output offline;

	private final String domain;

	private final int retry;

	/**
	 * @param retry 身份验证最大重试次数
	 * @param lang 默认语言
	 * @param domain 默认域
	 * @param getout
	 * @param offline
	 * @param statusBuilder
	 * @param vCardContext
	 * @param serverHeart
	 */
	public OnlineContextBuilder(int retry, String lang, String domain, Output offline, StatusBuilder statusBuilder, VCardContext vCardContext, Keepalive keepalive) {
		super();
		this.statusBuilder = statusBuilder;
		this.vCardContext = vCardContext;
		this.keepalive = keepalive;
		this.offline = offline;
		this.domain = domain;
		this.retry = retry;
		this.lang = lang;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		UserContext context = new UserContext(param);
		// 绑定JIDContext相关的出席状态
		context.statusCurrent = context.statusOnline = this.statusBuilder.build(context);
		return context;
	}

	private class UserContext implements JIDContext {

		private final long index = OnlineContextBuilder.this.indexes.incrementAndGet();

		private final AtomicInteger ping = new AtomicInteger(OnlineContextBuilder.this.pong);

		private final AtomicLong idle = new AtomicLong(System.currentTimeMillis());

		/**
		 * 预关闭标记
		 */
		private final AtomicBoolean prepareClose = new AtomicBoolean();

		/**
		 * 是否已出席
		 */
		private final AtomicBoolean presence = new AtomicBoolean();

		/**
		 * 同步出席锁
		 */
		private final ReentrantLock presenceLock = new ReentrantLock();

		/**
		 * 是否已验证身份
		 */
		private final AtomicBoolean auth = new AtomicBoolean();

		/**
		 * 已身份验证次数(Retry)
		 */
		private final AtomicInteger retry = new AtomicInteger();

		/**
		 * 是否已绑定
		 */
		private final AtomicBoolean binding = new AtomicBoolean();

		private final JIDContextParam param;

		/**
		 * 优先级
		 */
		private int priority = OnlineContextBuilder.this.priority;

		/**
		 * 默认使用离线JID(已连接未验证身份)
		 */
		private JID jid = OfflineJID.OFFLINE;

		private Status statusCurrent;

		private Status statusOnline;

		private Output outputOnline;

		private Output outputCurrent;

		private String domain;

		private String lang;

		public UserContext(JIDContextParam param) {
			super();
			this.param = param;
			this.outputCurrent = this.outputOnline = param.find(JIDContextParam.KEY_OUTPUT, Output.class);
		}

		public long index() {
			return this.index;
		}

		public boolean binding() {
			return this.binding.get();
		}

		public JIDContext bind() {
			this.binding.set(true);
			return this;
		}

		@Override
		public UserContext auth(boolean canAccess) {
			this.auth.set(canAccess);
			if (!canAccess) {
				this.retry.incrementAndGet();
			}
			return this;
		}

		@Override
		public boolean auth() {
			return this.auth.get();
		}

		public boolean authRetry() {
			return OnlineContextBuilder.this.retry >= this.retry.get();
		}

		public UserContext jid(JID jid) {
			this.jid = jid;
			return this;
		}

		public JID jid() {
			return this.jid;
		}

		@Override
		public boolean encrypt() {
			return this.param.find(JIDContextParam.KEY_STARTTLS, StartTls.class).startTls(this.domain());
		}

		public boolean encrypted() {
			return this.param.find(JIDContextParam.KEY_STARTTLS, StartTls.class).isTls(this.domain());
		}

		public JIDContext online() {
			try {
				this.presenceLock.lock();
				// Exchange
				this.outputCurrent = this.outputOnline;
				this.statusCurrent = this.statusOnline;
				this.presence.set(true);
				return this;
			} finally {
				this.presenceLock.unlock();
			}
		}

		public boolean onlined() {
			return this.presence.get();
		}

		public JIDContext offline() {
			try {
				this.presenceLock.lock();
				// Exchange
				OnlineContextBuilder.this.vCardContext.push(this.jid(), new BeanField<String>().name(VCardContext.FIELD_LOGOUT).value(String.valueOf(System.currentTimeMillis())));
				this.outputCurrent = OnlineContextBuilder.this.offline;
				this.statusCurrent = OfflineStatus.STATUS;
				this.presence.set(false);
				return this;
			} finally {
				this.presenceLock.unlock();
			}
		}

		/*
		 * 优先级(-127-127)
		 * 
		 * @see com.sissi.context.JIDContext#priority(int)
		 */
		@Override
		public JIDContext priority(int priority) {
			this.priority = priority < -128 ? -128 : priority > 127 ? 127 : priority;
			return this;
		}

		@Override
		public int priority() {
			return this.priority;
		}

		public long idle() {
			return this.idle.get();
		}

		@Override
		public Status status() {
			return this.statusCurrent;
		}

		@Override
		public SocketAddress address() {
			return this.param.find(JIDContextParam.KEY_ADDRESS, SocketAddress.class);
		}

		public JIDContext lang(String lang) {
			this.lang = lang;
			return this;
		}

		public String lang() {
			return this.lang != null ? this.lang : OnlineContextBuilder.this.lang;
		}

		public JIDContext domain(String domain) {
			this.jid.domain(this.domain = domain);
			return this;
		}

		public String domain() {
			return this.domain != null ? this.domain : OnlineContextBuilder.this.domain;
		}

		public JIDContext reset() {
			this.priority = OnlineContextBuilder.this.priority;
			this.lang = OnlineContextBuilder.this.lang;
			this.ping.set(OnlineContextBuilder.this.pong);
			this.prepareClose.set(false);
			this.presence.set(false);
			this.retry.set(0);
			return this;
		}

		@Override
		public boolean close() {
			try {
				if (this.closePrepare()) {
					// 清除出席状态,关闭输出流
					this.statusOnline.clear();
					this.outputOnline.close();
				}
				return true;
			} catch (Exception e) {
				return this.logFailed(e);
			}
		}

		public boolean closeTimeout() {
			return this.ping.get() != pong ? this.close() : false;
		}

		/*
		 * 预关闭,仅允许Input,禁止Output
		 * 
		 * @see com.sissi.context.JIDContext#closePrepare()
		 */
		public boolean closePrepare() {
			try {
				if (this.prepareClose.compareAndSet(false, true)) {
					this.offline();
				}
				return true;
			} catch (Exception e) {
				return this.logFailed(e);
			}
		}

		@Override
		public JIDContext ping() {
			OnlineContextBuilder.this.keepalive.ping(this);
			return this;
		}

		public JIDContext ping(int ping) {
			this.ping.set(ping);
			return this;
		}

		@Override
		public JIDContext pong(Element element) {
			// XMPP节存在ID且与内置PING.id相同则重置PING
			if (element.getId() != null && this.ping.get() == element.getId().hashCode()) {
				this.ping.set(OnlineContextBuilder.this.pong);
			}
			return this;
		}

		private JIDContext write(Element element, Output output, boolean bare) {
			try {
				// 忽略相同JID的消息回路
				if (this.jid().same(element.getFrom())) {
					OnlineContextBuilder.this.log.debug("Loop write: " + this.jid.asString() + " on " + element.getClass());
					return this;
				}
				// Binding -> 自动分配From
				output.output(this, this.binding() ? element.setTo(bare ? this.jid.asStringWithBare() : this.jid().asString()) : element);
			} finally {
				// 更新IDLE
				this.idle.set(System.currentTimeMillis());
			}
			return this;
		}

		@Override
		public JIDContext write(Element element) {
			return this.write(element, false);
		}

		public JIDContext write(Element element, boolean force) {
			return this.write(element, force, false);
		}

		public JIDContext write(Element element, boolean force, boolean bare) {
			return this.write(element, force ? this.outputOnline : this.outputCurrent, bare);
		}

		public JIDContext write(Collection<Element> elements) {
			return this.write(elements, false);
		}

		public JIDContext write(Collection<Element> elements, boolean force) {
			return this.write(elements, force, false);
		}

		public JIDContext write(Collection<Element> elements, boolean force, boolean bare) {
			for (Element element : elements) {
				try {
					this.write(element, force, bare);
				} catch (Exception e) {
					this.logFailed(e);
				}
			}
			return this;
		}

		private boolean logFailed(Exception e) {
			OnlineContextBuilder.this.log.debug(e.toString());
			Trace.trace(OnlineContextBuilder.this.log, e);
			return false;
		}
	}
}
