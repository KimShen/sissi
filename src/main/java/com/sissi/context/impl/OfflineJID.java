package com.sissi.context.impl;

import com.sissi.context.JID;

/**
 * 离线JID,用于未登陆的用户(已连接未验证身份)
 * 
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

	public boolean like(JID jid) {
		return false;
	}

	public boolean like(String jid) {
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

	public JID clone() {
		return this;
	}

	public boolean isBare() {
		return true;
	}

	public boolean isGroup() {
		return false;
	}

	public boolean same(JID jid) {
		return false;
	}

	public boolean same(String jid) {
		return false;
	}

	public boolean valid() {
		return false;
	}

	public boolean valid(boolean excludeDomain) {
		return false;
	}

	@Override
	public String asString() {
		return null;
	}

	@Override
	public String asStringWithBare() {
		return null;
	}

	public String asString(boolean bare) {
		return null;
	}
}