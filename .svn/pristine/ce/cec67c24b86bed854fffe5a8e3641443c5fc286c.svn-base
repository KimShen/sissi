package com.sissi.context.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.JIDContextPresence;
import com.sissi.context.JIDContextPresenceBuilder;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Protocol;

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

		private final Lock lock = new ReentrantLock();

		private final AtomicBoolean isAuth = new AtomicBoolean();

		private JIDContextPresence myPresence;

		private Output output;

		private JID jid;

		public UserContext(Output output) {
			super();
			this.output = output;
		}

		public Boolean isLogining() {
			// First thread into this method the lock can be grab
			if (this.lock.tryLock()) {
				return this.isAuth.get();
			} else {
				// The subsequent thread always return true until setAuth will be invoke
				return true;
			}
		}

		@Override
		public Boolean isAuth() {
			return this.isAuth.get();
		}

		@Override
		public UserContext setAuth(Boolean canAccess) {
			this.isAuth.set(canAccess);
			this.lock.unlock();
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
		public void write(Protocol protocol) {
			this.output.output(this, protocol);
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
