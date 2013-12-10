package com.sissi.context.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.JIDContextBuilder;
import com.sissi.context.JIDContext.JIDContextParam;
import com.sissi.context.OnlineStatus;
import com.sissi.context.OnlineStatus.OnlineStatusBuilder;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-19
 */
public class OnlineContextBuilder implements JIDContextBuilder {

	public final static String KEY_OUTPUT = "OUTPUT";

	private final AtomicLong indexes = new AtomicLong();

	private final OnlineStatusBuilder onlineStatusBuilder;

	public OnlineContextBuilder(OnlineStatusBuilder onlineStatusBuilder) {
		super();
		this.onlineStatusBuilder = onlineStatusBuilder;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		UserContext context = new UserContext(param.find(KEY_OUTPUT, Output.class));
		context.onlineStatus = this.onlineStatusBuilder.build(context);
		return context;
	}

	private class UserContext implements JIDContext {

		private final AtomicBoolean isBinding = new AtomicBoolean();

		private final AtomicBoolean isAuth = new AtomicBoolean();

		private final Output output;

		private final Long index;

		private OnlineStatus onlineStatus;

		private Integer priority;

		private JID jid;

		public UserContext(Output output) {
			super();
			this.output = output;
			this.priority = 0;
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
		public Boolean isAuth() {
			return this.isAuth.get();
		}

		@Override
		public UserContext setAuth(Boolean canAccess) {
			this.isAuth.set(canAccess);
			return this;
		}

		public UserContext setJid(JID jid) {
			this.jid = jid;
			return this;
		}

		public JID getJid() {
			return this.jid;
		}

		@Override
		public JIDContext write(Element node) {
			this.output.output(this, node);
			return this;
		}

		@Override
		public Boolean close() {
			this.output.close();
			return true;
		}

		@Override
		public OnlineStatus getOnlineStatus() {
			return this.onlineStatus;
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
	}

	public static class UserContextParam implements JIDContextParam {

		private final Output output;

		public UserContextParam(Output output) {
			super();
			this.output = output;
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			return OnlineContextBuilder.KEY_OUTPUT.equals(key) ? clazz.cast(output) : null;
		}
	}
}
