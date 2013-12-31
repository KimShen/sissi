package com.sissi.protocol.muc;

/**
 * @author kim 2013年12月30日
 */
public enum Affiliation {

	OWNER, ADMIN, MEMBER;

	public String toString() {
		return super.toString().toLowerCase();
	}

	public Boolean equals(String type) {
		return this == Affiliation.parse(type);
	}

	public static Affiliation parse(String affiliation) {
		return Affiliation.valueOf(affiliation.toUpperCase());
	}
}
