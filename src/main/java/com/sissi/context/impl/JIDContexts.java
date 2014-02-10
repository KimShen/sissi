package com.sissi.context.impl;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-21
 */
public class JIDContexts extends ArrayList<JIDContext> implements JIDContext {

	private final static long serialVersionUID = 1L;

	private final static RuntimeException NOT_SUPPORT = new RuntimeException("MultiContexts not support this function");

	public JIDContext bind() {
		for (JIDContext each : this) {
			each.bind();
		}
		return this;
	}

	private JIDContext assertOnly() {
		if (super.size() == 1) {
			return super.get(0);
		}
		throw NOT_SUPPORT;
	}

	public boolean binding() {
		boolean binding = true;
		for (JIDContext each : this) {
			binding = each.binding() ? binding : false;
		}
		return binding;
	}

	@Override
	public JIDContext priority(int priority) {
		for (JIDContext each : this) {
			each.priority(priority);
		}
		return this;
	}

	public JIDContext domain(String domain) {
		for (JIDContext each : this) {
			each.domain(domain);
		}
		return this;
	}

	public JIDContext lang(String lang) {
		for (JIDContext each : this) {
			each.lang(lang);
		}
		return this;
	}

	@Override
	public JIDContext jid(JID jid) {
		for (JIDContext each : this) {
			each.jid(jid);
		}
		return this;
	}

	@Override
	public boolean encrypt() {
		boolean encrypt = true;
		for (JIDContext each : this) {
			encrypt = each.encrypt() ? encrypt : false;
		}
		return encrypt;
	}

	public boolean encrypted() {
		boolean encrypted = true;
		for (JIDContext each : this) {
			encrypted = each.encrypted() ? encrypted : false;
		}
		return encrypted;
	}

	public JIDContext present() {
		for (JIDContext each : this) {
			each.present();
		}
		return this;
	}

	public boolean presented() {
		boolean presented = true;
		for (JIDContext each : this) {
			presented = each.presented() ? presented : false;
		}
		return presented;
	}

	@Override
	public JIDContext auth(boolean canAccess) {
		for (JIDContext each : this) {
			each.auth(canAccess);
		}
		return this;
	}

	@Override
	public boolean auth() {
		boolean auth = true;
		for (JIDContext each : this) {
			auth = each.auth() ? auth : false;
		}
		return auth;
	}

	public boolean authRetry() {
		boolean authRetry = true;
		for (JIDContext each : this) {
			authRetry = each.authRetry() ? authRetry : false;
		}
		return authRetry;
	}

	@Override
	public boolean close() {
		boolean allClose = true;
		for (JIDContext each : this) {
			allClose = each.close() ? allClose : true;
		}
		return allClose;
	}

	public boolean closePrepare() {
		boolean allClose = true;
		for (JIDContext each : this) {
			allClose = each.closePrepare() ? allClose : true;
		}
		return allClose;
	}

	public boolean closeTimeout() {
		boolean allClose = true;
		for (JIDContext each : this) {
			allClose = each.closeTimeout() ? allClose : true;
		}
		return allClose;
	}

	public JIDContext reset() {
		for (JIDContext each : this) {
			each.reset();
		}
		return this;
	}

	public JIDContext ping() {
		for (JIDContext each : this) {
			each.ping();
		}
		return this;
	}

	public JIDContext pong(Element node) {
		for (JIDContext each : this) {
			each.pong(node);
		}
		return this;
	}

	@Override
	public JIDContext write(Element node) {
		for (JIDContext each : this) {
			each.write(node);
		}
		return this;
	}

	public JIDContext write(Collection<Element> nodes) {
		for (Element node : nodes) {
			this.write(node);
		}
		return this;
	}

	public long index() {
		return this.assertOnly().index();
	}

	@Override
	public JID jid() {
		return this.assertOnly().jid();
	}

	public String lang() {
		return this.assertOnly().lang();
	}

	public String domain() {
		return this.assertOnly().domain();
	}

	@Override
	public Status status() {
		return this.assertOnly().status();
	}

	@Override
	public int priority() {
		return this.assertOnly().priority();
	}

	public long idle() {
		return this.assertOnly().idle();
	}

	public SocketAddress address() {
		return this.assertOnly().address();
	}
}
