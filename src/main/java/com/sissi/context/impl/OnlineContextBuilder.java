package com.sissi.context.impl;

import java.util.concurrent.atomic.AtomicBoolean;
<<<<<<< HEAD
import java.util.concurrent.atomic.AtomicLong;
=======
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.JIDContextBuilder;
import com.sissi.context.JIDContext.JIDContextParam;
import com.sissi.context.MyPresence;
import com.sissi.context.MyPresence.MyPresenceBuilder;
import com.sissi.pipeline.Output;
<<<<<<< HEAD
import com.sissi.protocol.Element;
=======
import com.sissi.protocol.Node;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

/**
 * @author kim 2013-11-19
 */
public class OnlineContextBuilder implements JIDContextBuilder {

	public final static String KEY_OUTPUT = "OUTPUT";

	private final AtomicLong indexes = new AtomicLong();

	private final MyPresenceBuilder myPresenceBuilder;

	public OnlineContextBuilder(MyPresenceBuilder myPresenceBuilder) {
		super();
		this.myPresenceBuilder = myPresenceBuilder;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		UserContext context = new UserContext(param.find(KEY_OUTPUT, Output.class));
		context.myPresence = this.myPresenceBuilder.build(context);
		return context;
	}

	private class UserContext implements JIDContext {

		private final AtomicBoolean isBinding = new AtomicBoolean();
<<<<<<< HEAD

		private final AtomicBoolean isAuth = new AtomicBoolean();

		private MyPresence myPresence;

		private Integer priority;
=======
		
		private final AtomicBoolean isLogin = new AtomicBoolean();

		private final AtomicBoolean isAuth = new AtomicBoolean();
		
		private JIDContextPresence myPresence;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

		private Output output;

		private Long index;

		private JID jid;

		public UserContext(Output output) {
			super();
			this.output = output;
			this.priority = 0;
			this.index = OnlineContextBuilder.this.indexes.incrementAndGet();
		}

<<<<<<< HEAD
		public Long getIndex() {
			return index;
=======
		public Boolean isLogining() {
			return this.isLogin.get() ? true : !this.isLogin.compareAndSet(false, true);
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
		}

		public Boolean isBinding() {
			return this.isBinding.get();
<<<<<<< HEAD
		}

		public JIDContext setBinding(Boolean isBinding) {
			this.isBinding.set(isBinding);
			return this;
=======
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
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
<<<<<<< HEAD
		public void write(Element node) {
=======
		public void write(Node node) {
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
			this.output.output(this, node);
		}

		@Override
		public Boolean close() {
			this.output.close();
			return true;
		}

		@Override
		public MyPresence getPresence() {
			return this.myPresence;
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
