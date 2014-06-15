package com.sissi.protocol.presence;

/**
 * @author kim 2014年1月16日
 */
public enum PresenceType {

	NONE, PROBE, SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBE, UNSUBSCRIBED, UNAVAILABLE, AVAILABLE;

	public String toString() {
		if (AVAILABLE == this) {
			return null;
		}
		return super.toString().toLowerCase();
	}

	public boolean equals(String type) {
		return this == parse(type);
	}

	public boolean in(PresenceType... types) {
		for (PresenceType type : types) {
			if (this == type) {
				return true;
			}
		}
		return false;
	}

	public static PresenceType parse(String type) {
		try {
			return type == null ? AVAILABLE : PresenceType.valueOf(type.toUpperCase());
		} catch (Exception e) {
			return NONE;
		}
	}
}
