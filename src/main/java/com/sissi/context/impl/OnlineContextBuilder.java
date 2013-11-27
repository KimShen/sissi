package com.sissi.context.impl;

import java.util.concurrent.atomic.AtomicBoolean;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.JIDContextPresence;
import com.sissi.context.JIDContextPresenceBuilder;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Node;

/**
 * @author kim 2013-11-19
 */
public class OnlineContextBuilder implements JIDContextBuilder {

	public final static String KEY_OUTPUT = "OUTPUT";

	private JIDContextPresenceBuilder jidContextPresenceBuilder;

	public OnlineContextBuilder(JIDContextPresenceBuilder jidContextPresenceBuilder) {
		super();
		this.jidContextPresenceBuilder = jidContextPresenceBuilder;
	}

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		UserContext context = new UserContext(param.find(KEY_OUTPUT, Output.class));
		context.myPresence = this.jidContextPresenceBuilder.build(context);
		return context;
	}

	private class UserContext implements JIDContext {

		private final AtomicBoolean isBinding = new AtomicBoolean();
		
		private final AtomicBoolean isLogin = new AtomicBoolean();

		private final AtomicBoolean isAuth = new AtomicBoolean();
		
		private JIDContextPresence myPresence;

		private Output output;

		private JID jid;

		public UserContext(Output output) {
			super();
			this.output = output;
		}

		public Boolean isLogining() {
			return this.isLogin.get() ? true : !this.isLogin.compareAndSet(false, true);
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
		public void write(Node node) {
			this.output.output(this, node);
		}

		@Override
		public Boolean close() {
			this.output.close();
			return true;
		}

		@Override
		public JIDContextPresence getPresence() {
			return this.myPresence;
		}
	}
}
