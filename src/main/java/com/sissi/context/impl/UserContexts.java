package com.sissi.context.impl;

import java.util.ArrayList;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextPresence;
import com.sissi.protocol.Node;

/**
 * @author kim 2013-11-21
 */
public class UserContexts extends ArrayList<JIDContext> implements JIDContext {

	private static final long serialVersionUID = 1L;

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
	public JIDContext setJid(JID jid) {
		for (JIDContext each : this) {
			each.setJid(jid);
		}
		return this;
	}

	@Override
	public JID getJid() {
		throw new RuntimeException("MultiContexts not support this funciton");
	}

	@Override
	public JIDContextPresence getPresence() {
		throw new RuntimeException("MultiContexts not support this funciton");
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
	public Boolean isLogining() {
		boolean isLoging = true;
		for (JIDContext each : this) {
			isLoging = each.isLogining();
		}
		return isLoging;
	}

	@Override
	public Boolean close() {
		boolean allClose = true;
		for (JIDContext each : this) {
			allClose = each.close();
		}
		return allClose;
	}

	@Override
	public void write(Node node) {
		for (JIDContext each : this) {
			each.write(node);
		}
	}
}
