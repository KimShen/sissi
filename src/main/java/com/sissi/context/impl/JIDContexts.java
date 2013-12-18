package com.sissi.context.impl;

import java.util.ArrayList;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-21
 */
public class JIDContexts extends ArrayList<JIDContext> implements JIDContext {

	private static final long serialVersionUID = 1L;

	private final RuntimeException NOT_SUPPORT = new RuntimeException("MultiContexts not support this funciton");

	@Override
	public JIDContext setAuth(Boolean canAccess) {
		for (JIDContext each : this) {
			each.setAuth(canAccess);
		}
		return this;
	}

	public JIDContext setBinding(Boolean isBinding) {
		for (JIDContext each : this) {
			each.setBinding(isBinding);
		}
		return this;
	}

	@Override
	public JIDContext setPriority(Integer priority) {
		for (JIDContext each : this) {
			each.setPriority(priority);
		}
		return this;
	}

	@Override
	public JIDContext setJid(JID jid) {
		for (JIDContext each : this) {
			each.setJid(jid);
		}
		return this;
	}

	@Override
	public JIDContext setStarttls() {
		for (JIDContext each : this) {
			each.setStarttls();
		}
		return this;
	}

	@Override
	public Boolean isStarttls() {
		boolean isStarttls = true;
		for (JIDContext each : this) {
			isStarttls = each.isStarttls();
		}
		return isStarttls;
	}

	@Override
	public Boolean isAuth() {
		boolean isAuth = true;
		for (JIDContext each : this) {
			isAuth = each.isAuth();
		}
		return isAuth;
	}

	public Boolean isBinding() {
		boolean isBinding = true;
		for (JIDContext each : this) {
			isBinding = each.isBinding();
		}
		return isBinding;
	}

	@Override
	public Boolean close() {
		boolean allClose = true;
		for (JIDContext each : this) {
			if (!each.close()) {
				allClose = false;
			}
		}
		return allClose;
	}

	@Override
	public JIDContext write(Element node) {
		for (JIDContext each : this) {
			each.write(node);
		}
		return this;
	}

	public Long getIndex() {
		throw NOT_SUPPORT;
	}

	@Override
	public JID getJid() {
		throw NOT_SUPPORT;
	}

	@Override
	public Integer getPriority() {
		throw NOT_SUPPORT;
	}

	@Override
	public Status getStatus() {
		throw NOT_SUPPORT;
	}
}
