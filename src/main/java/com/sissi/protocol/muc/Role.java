package com.sissi.protocol.muc;

/**
 * @author kim 2013年12月30日
 */
public enum Role {

	MODERATOR, PARTICIPANT;

	public String toString() {
		return super.toString().toLowerCase();
	}

	public Boolean equals(String type) {
		return this == Role.parse(type);
	}

	public static Role parse(String role) {
		return Role.valueOf(role.toUpperCase());
	}
}
