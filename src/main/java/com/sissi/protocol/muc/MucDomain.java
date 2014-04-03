package com.sissi.protocol.muc;

/**
 * @author kim 2014年4月3日
 */
public enum MucDomain {

	ROOMINFO_OCCUPANTS;

	private final static String prefix = "muc#";

	public String toString() {
		return prefix + super.toString().toLowerCase();
	}
}
