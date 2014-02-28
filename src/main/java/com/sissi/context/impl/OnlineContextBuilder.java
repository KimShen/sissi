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
import com.sissi.pipeline.Output;
import com.sissi.protocol.Element;
import com.sissi.server.ServerHeart;
import com.sissi.server.tls.ServerTls;
import com.sissi.ucenter.VCardContext;
import com.sissi.ucenter.field.impl.BeanField;

/**
 * @author kim 2013-11-19
 */
public class OnlineContextBuilder implements JIDContextBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final AtomicLong indexes = new AtomicLong();

	private final int priority = -1;

	private final int pong = -1;

	private final StatusBuilder statusBuilder;

	private final VCardContext vCardContext;

	private final ServerHeart serverHeart;

	private final int authRetry;

	private final Output offline;

	private final String domain;

	private final String lang;

	public OnlineContextBuilder(int authRetry, String lang, String domain, Output offline, StatusBuilder statusBuilder, VCardContext vCardContext, ServerHeart serverHeart) {
		super();
		this.statusBuilder = statusBuilder;
		this.vCardContext = vCardContext;
		this.serverHeart = serverHeart;
		this.authRetry = authRetry;
		this.offline = offline;
		this.domain = domain;
		this.lang = lang;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		UserContext context = new UserContext(param);
		// two-way reference
		context.statusCurrent = context.statusOnline = this.statusBuilder.build(context);
		return context;
	}

	private class UserContext implements JIDContext {

		private final long index = OnlineContextBuilder.this.indexes.incrementAndGet();

		private final AtomicInteger ping = new AtomicInteger(OnlineContextBuilder.this.pong);

		private final AtomicLong idle = new AtomicLong(System.currentTimeMillis());

		private final AtomicBoolean prepareClose = new AtomicBoolean();

		private final AtomicBoolean presence = new AtomicBoolean();

		private final ReentrantLock presenceLock = new ReentrantLock();

		private final AtomicInteger authRetry = new AtomicInteger();

		private final AtomicBoolean auth = new AtomicBoolean();

		private final AtomicBoolean binding = new AtomicBoolean();

		private final JIDContextParam param;

		private int priority = OnlineContextBuilder.this.priority;

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
				this.authRetry.incrementAndGet();
			}
			return this;
		}

		@Override
		public boolean auth() {
			return this.auth.get();
		}

		public boolean authRetry() {
			return OnlineContextBuilder.this.authRetry >= this.authRetry.get();
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
			return this.param.find(JIDContextParam.KEY_SERVERTLS, ServerTls.class).startTls(this.domain());
		}

		public boolean encrypted() {
			return this.param.find(JIDContextParam.KEY_SERVERTLS, ServerTls.class).isTls(this.domain());
		}

		public JIDContext online() {
			try {
				this.presenceLock.lock();
				this.outputCurrent = this.outputOnline;
				this.statusCurrent = this.statusOnline;
				this.presence.set(true);
				return this;
			} finally {
				this.presenceLock.unlock();
			}
		}

		public JIDContext offline() {
			try {
				this.presenceLock.lock();
				this.outputCurrent = OnlineContextBuilder.this.offline;
				this.statusCurrent = OfflineStatus.STATUS;
				this.presence.set(false);
				return this;
			} finally {
				this.presenceLock.unlock();
			}
		}

		public boolean presence() {
			return this.presence.get();
		}

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
			this.domain = domain;
			this.jid.domain(this.domain);
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
			this.authRetry.set(0);
			return this;
		}

		@Override
		public boolean close() {
			try {
				if (this.closePrepare()) {
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

		public boolean closePrepare() {
			try {
				if (this.prepareClose.compareAndSet(false, true)) {
					this.outputCurrent = OnlineContextBuilder.this.offline;
					this.statusCurrent = OfflineStatus.STATUS;
					OnlineContextBuilder.this.vCardContext.set(this.jid(), new BeanField<String>().setName(VCardContext.FIELD_LOGOUT).setValue(String.valueOf(System.currentTimeMillis())));
				}
				return true;
			} catch (Exception e) {
				return this.logFailed(e);
			}
		}

		@Override
		public JIDContext ping() {
			this.ping.set(OnlineContextBuilder.this.serverHeart.ping(this).hashCode());
			return this;
		}

		@Override
		public JIDContext pong(Element element) {
			if (this.ping.get() == element.getId().hashCode()) {
				this.ping.set(pong);
			}
			return this;
		}

		private JIDContext write(Element element, Output output, boolean bare) {
			try {
				if (!this.jid().same(element.getFrom())) {
					output.output(this, element.setTo(bare ? this.jid.asStringWithBare() : this.jid().asString()));
				}
			} finally {
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
