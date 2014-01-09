package com.sissi.context.impl;

import com.sissi.context.JID;

/**
 * @author kim 2014年1月3日
 */
class OfflineJID implements JID {

	public final static JID OFFLINE = new OfflineJID();

	private OfflineJID() {

	}

	@Override
	public String getUser() {
		return "";
	}

	@Override
	public String getDomain() {
		return "";
	}

	public JID setDomain(String domain) {
		return this;
	}

	@Override
	public String getResource() {
		return "";
	}

	@Override
	public JID setResource(String resource) {
		return this;
	}

	@Override
	public JID getBare() {
		return this;
	}

	@Override
	public String asString() {
		return "";
	}

	@Override
	public String asStringWithBare() {
		return "";
	}
}