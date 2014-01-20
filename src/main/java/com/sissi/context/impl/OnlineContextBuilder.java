package com.sissi.context.impl;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.Status;
import com.sissi.context.StatusBuilder;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Element;
import com.sissi.server.ServerHeart;
import com.sissi.server.ServerTls;

/**
 * @author kim 2013-11-19
 */
public class OnlineContextBuilder implements JIDContextBuilder {

	public final static String KEY_OUTPUT = "OUTPUT";

	public final static String KEY_ADDRESS = "ADDRESS";

	public final static String KEY_SERVERTLS = "TLS";

	private final static Long PONG = -1L;

	private final Log log = LogFactory.getLog(this.getClass());

	private final AtomicLong indexes = new AtomicLong();

	private final Integer priority = 0;

	private final StatusBuilder statusBuilder;

	private final ServerHeart serverHeart;

	private final Integer authRetry;

	private final String domain;

	private final String lang;

	public OnlineContextBuilder(Integer authRetry, String lang, String domain, StatusBuilder statusBuilder, ServerHeart serverHeart) {
		super();
		this.statusBuilder = statusBuilder;
		this.serverHeart = serverHeart;
		this.authRetry = authRetry;
		this.domain = domain;
		this.lang = lang;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		UserContext context = new UserContext(param);
		context.status = this.statusBuilder.build(context);
		return context;
	}

	private class UserContext implements JIDContext {

		private final AtomicLong ping = new AtomicLong(OnlineContextBuilder.PONG);

		private final AtomicBoolean isAuth = new AtomicBoolean();

		private final AtomicBoolean isBinding = new AtomicBoolean();

		private final AtomicBoolean isPrepareClose = new AtomicBoolean();

		private final AtomicInteger authRetry = new AtomicInteger();

		private final Long index;

		private final Output output;

		private final ServerTls serverTls;

		private final SocketAddress address;

		private JID jid = OfflineJID.OFFLINE;

		private Integer priority;

		private Status status;

		private String domain;

		private String lang;

		public UserContext(JIDContextParam param) {
			super();
			this.output = param.find(KEY_OUTPUT, Output.class);
			this.address = param.find(KEY_ADDRESS, SocketAddress.class);
			this.serverTls = param.find(KEY_SERVERTLS, ServerTls.class);
			this.priority = OnlineContextBuilder.this.priority;
			this.index = OnlineContextBuilder.this.indexes.incrementAndGet();
		}

		public Long getIndex() {
			return this.index;
		}

		public Boolean isBinding() {
			return this.isBinding.get();
		}

		public JIDContext setBinding(Boolean isBinding) {
			this.isBinding.set(isBinding);
			return this;
		}

		@Override
		public UserContext setAuth(Boolean canAccess) {
			this.isAuth.set(canAccess);
			if (!canAccess) {
				this.authRetry.incrementAndGet();
			}
			return this;
		}

		@Override
		public Boolean isAuth() {
			return this.isAuth.get();
		}

		public Boolean isAuthRetry() {
			return OnlineContextBuilder.this.authRetry >= this.authRetry.get();
		}

		public UserContext setJid(JID jid) {
			this.jid = jid;
			return this;
		}

		public JID getJid() {
			return this.jid;
		}

		@Override
		public Boolean setTls() {
			return this.serverTls.startTls(this.getDomain());
		}

		public Boolean isTls() {
			return this.serverTls.isTls(this.getDomain());
		}

		@Override
		public JIDContext setPriority(Integer priority) {
			this.priority = priority;
			return this;
		}

		@Override
		public Integer getPriority() {
			return this.priority;
		}

		@Override
		public Status getStatus() {
			return this.status;
		}

		@Override
		public SocketAddress getAddress() {
			return this.address;
		}

		public JIDContext setLang(String lang) {
			this.lang = lang;
			return this;
		}

		public String getLang() {
			return this.lang != null ? this.lang : OnlineContextBuilder.this.lang;
		}

		public JIDContext setDomain(String domain) {
			this.domain = domain;
			this.jid.setDomain(this.domain);
			return this;
		}

		public String getDomain() {
			return this.domain != null ? this.domain : OnlineContextBuilder.this.domain;
		}

		public JIDContext reset() {
			this.priority = OnlineContextBuilder.this.priority;
			this.lang = OnlineContextBuilder.this.lang;
			this.ping.set(OnlineContextBuilder.PONG);
			this.isPrepareClose.set(false);
			this.authRetry.set(0);
			return this;
		}

		@Override
		public Boolean close() {
			if (this.closePrepare()) {
				this.output.close();
			}
			return true;
		}

		public Boolean closeTimeout() {
			if (this.ping.get() != PONG) {
				this.close();
			}
			return true;
		}

		public Boolean closePrepare() {
			if (this.isPrepareClose.compareAndSet(false, true)) {
				this.status.clear();
				this.status = null;
			}
			return true;
		}

		@Override
		public JIDContext ping() {
			this.ping.set(OnlineContextBuilder.this.serverHeart.ping(this));
			return this;
		}

		@Override
		public JIDContext pong(Element element) {
			try {
				if (this.ping.get() == Long.valueOf(element.getId())) {
					this.ping.set(PONG);
				}
			} catch (Exception e) {
			}
			return this;
		}

		@Override
		public JIDContext write(Element node) {
			if (!this.isPrepareClose.get()) {
				this.output.output(this, node.setTo(this.getJid().asString()));
			}
			return this;
		}

		public JIDContext write(Collection<Element> elements) {
			for (Element element : elements) {
				try {
					this.write(element);
				} catch (Exception e) {
					if(OnlineContextBuilder.this.log.isWarnEnabled()){
						OnlineContextBuilder.this.log.warn(e.toString());
						e.printStackTrace();
					}
				}
			}
			return this;
		}
	}
}
