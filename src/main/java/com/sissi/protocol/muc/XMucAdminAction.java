package com.sissi.protocol.muc;

/**
 * @author kim 2014年3月19日
 */
public enum XMucAdminAction {

	ROLE, AFFILIATION, NONE;

	public static XMucAdminAction parse(String action) {
		try {
			return XMucAdminAction.valueOf(action.toUpperCase());
		} catch (Exception e) {
			return XMucAdminAction.NONE;
		}
	}
}
