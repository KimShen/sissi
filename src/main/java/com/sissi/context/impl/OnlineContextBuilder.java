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
import com.sissi.ucenter.VCardContext;
import com.sissi.ucenter.field.impl.BeanField;

/**
 * @author kim 2013-11-19
 */
public class OnlineContextBuilder implements JIDContextBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final AtomicLong indexes = new AtomicLong();

	private final JID jid = new OfflineJID();

	private final int priority = 0;

	private final long pong = -1L;

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
		context.status = this.statusBuilder.build(context);
		return context;
	}

	private class UserContext implements JIDContext {

		private final AtomicLong ping = new AtomicLong(OnlineContextBuilder.this.pong);

		private final AtomicLong idle = new AtomicLong(System.currentTimeMillis());

		private final AtomicBoolean prepareClose = new AtomicBoolean();

		private final AtomicBoolean presence = new AtomicBoolean();

		private final AtomicBoolean binding = new AtomicBoolean();

		private final AtomicBoolean auth = new AtomicBoolean();

		private final AtomicInteger authRetry = new AtomicInteger();

		private final long index;

		private final ServerTls serverTls;

		private final SocketAddress address;

		private int priority;

		private Output output;

		private Output backup;

		private Status status;

		private String domain;

		private String lang;

		private JID jid;

		public UserContext(JIDContextParam param) {
			super();
			this.index = OnlineContextBuilder.this.indexes.incrementAndGet();
			this.address = param.find(JIDContextParam.KEY_ADDRESS, SocketAddress.class);
			this.serverTls = param.find(JIDContextParam.KEY_SERVERTLS, ServerTls.class);
			this.output = param.find(JIDContextParam.KEY_OUTPUT, Output.class);
			this.priority = OnlineContextBuilder.this.priority;
			this.jid = OnlineContextBuilder.this.jid;
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
			return this.serverTls.startTls(this.domain());
		}

		public boolean encrypted() {
			return this.serverTls.isTls(this.domain());
		}

		public JIDContext present() {
			this.presence.set(true);
			return this;
		}

		public boolean presented() {
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
			return this.status;
		}

		@Override
		public SocketAddress address() {
			return this.address;
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
					this.backup.close();
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
					this.backup = this.output;
					this.output = OnlineContextBuilder.this.offline;
					this.status.clear();
					this.status = OfflineStatus.STATUS;
					OnlineContextBuilder.this.vCardContext.set(this.jid(), new BeanField<String>().setName(VCardContext.FIELD_LOGOUT).setValue(String.valueOf(System.currentTimeMillis())));
				}
				return true;
			} catch (Exception e) {
				return this.logFailed(e);
			}
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
					this.ping.set(pong);
				}
			} catch (Exception e) {
				this.logFailed(e);
			}
			return this;
		}

		@Override
		public JIDContext write(Element node) {
			this.output.output(this, node.setTo(this.jid().asString()));
			this.idle.set(System.currentTimeMillis());
			return this;
		}

		public JIDContext write(Collection<Element> elements) {
			for (Element element : elements) {
				try {
					this.write(element);
				} catch (Exception e) {
					this.logFailed(e);
				}
			}
			return this;
		}

		private boolean logFailed(Exception e) {
			if (OnlineContextBuilder.this.log.isDebugEnabled()) {
				OnlineContextBuilder.this.log.debug(e.toString());
				e.printStackTrace();
			}
			return false;
		}
	}

	private class OfflineJID implements JID {

		private OfflineJID() {

		}

		@Override
		public String user() {
			return null;
		}

		public boolean user(JID jid) {
			return false;
		}

		public boolean user(String jid) {
			return false;
		}

		@Override
		public String domain() {
			return null;
		}

		public JID domain(String domain) {
			return this;
		}

		@Override
		public String resource() {
			return null;
		}

		@Override
		public JID resource(String resource) {
			return this;
		}

		@Override
		public JID bare() {
			return this;
		}

		public boolean isBare() {
			return false;
		}

		public boolean valid() {
			return true;
		}

		public boolean valid(boolean excludeDomain) {
			return true;
		}

		@Override
		public String asString() {
			return null;
		}

		@Override
		public String asStringWithBare() {
			return null;
		}
	}
}
