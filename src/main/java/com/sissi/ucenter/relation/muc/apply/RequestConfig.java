package com.sissi.ucenter.relation.muc.apply;

/**
 * @author kim 2014年4月24日
 */
public enum RequestConfig {

	JID, GROUP, ROLE, ROOMNICK, REQUEST_ALLOW;

	private final static String prefix = "muc#request_";

	public String toString() {
		return prefix + super.toString().toLowerCase();
	}
}
