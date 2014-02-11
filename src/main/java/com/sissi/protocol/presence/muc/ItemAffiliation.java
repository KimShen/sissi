package com.sissi.protocol.presence.muc;

/**
 * @author kim 2014年1月16日
 */
public enum ItemAffiliation {

	OWNER, ADMIN, MEMBER;

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String type) {
		return this == ItemAffiliation.parse(type);
	}

	public boolean in(ItemAffiliation... types) {
		for (ItemAffiliation type : types) {
			if (this == type) {
				return true;
			}
		}
		return false;
	}

	public static ItemAffiliation parse(String type) {
		try {
			return ItemAffiliation.valueOf(type.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}
}