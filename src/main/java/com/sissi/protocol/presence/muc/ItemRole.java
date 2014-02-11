package com.sissi.protocol.presence.muc;

/**
 * @author kim 2014年1月16日
 */
public enum ItemRole {

	MODERATOR, PARTICIPANT, VISITOR;

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String type) {
		return this == ItemRole.parse(type);
	}

	public boolean in(ItemRole... types) {
		for (ItemRole type : types) {
			if (this == type) {
				return true;
			}
		}
		return false;
	}

	public static ItemRole parse(String type) {
		try {
			return ItemRole.valueOf(type.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}
}