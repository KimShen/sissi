package com.sissi.context.impl;

import com.sissi.context.JID;

/**
 * @author kim 2014年2月11日
 */
public class OfflineJID implements JID {

	public final static JID OFFLINE = new OfflineJID();

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

	public boolean isGroup() {
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