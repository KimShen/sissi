package com.sissi.context.impl;

import com.sissi.context.JID;

/**
 * @author kim 2014年1月3日
 */
class OfflineJID implements JID {

	public final static JID JID = new OfflineJID();

	private OfflineJID() {

	}

	@Override
	public String getUser() {
		return null;
	}

	@Override
	public String getDomain() {
		return null;
	}

	public JID setDomain(String domain) {
		return this;
	}

	@Override
	public String getResource() {
		return null;
	}

	@Override
	public JID setResource(String resource) {
		return this;
	}

	@Override
	public JID getBare() {
		return this;
	}

	public Boolean isValid() {
		return true;
	}

	public Boolean isValid(Boolean includeDomain) {
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