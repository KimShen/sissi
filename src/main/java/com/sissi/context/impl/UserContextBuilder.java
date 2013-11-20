package com.sissi.context.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.MyPresence;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-19
 */
public class UserContextBuilder implements JIDContextBuilder {

	final static String KEY_OUTPUT = "OUTPUT";

	@Override
	public JIDContext build(JID jid, JIDContextParam param) {
		return new UserContext(param.find(KEY_OUTPUT, Output.class));
	}

	private class UserContext implements JIDContext {

		private final Lock lock = new ReentrantLock();

		private final AtomicBoolean isAuth = new AtomicBoolean();

		private final MyPresence myPresence = new UserPresence();

		private JID jid;

		private Output output;

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
		public MyPresence getPresence() {
			return this.myPresence;
		}
	}

	private class UserPresence implements MyPresence {

		private String type;

		private String show;

		private String status;

		@Override
		public String type() {
			return this.type;
		}

		@Override
		public String show() {
			return this.show;
		}

		@Override
		public String status() {
			return this.status;
		}

		@Override
		public String type(String type) {
			this.type = type;
			return this.type;
		}

		@Override
		public String show(String show) {
			this.show = show;
			return this.show;
		}

		@Override
		public String status(String status) {
			this.status = status;
			return this.status;
		}
	}
}
