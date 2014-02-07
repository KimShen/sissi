package com.sissi.protocol.presence;

/**
 * @author kim 2014年1月16日
 */
public enum PresenceType {

	PROBE, SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBE, UNSUBSCRIBED, UNAVAILABLE, AVAILABLE;

	public String toString() {
		if (AVAILABLE == this) {
			return null;
		}
		return super.toString().toLowerCase();
	}

	public Boolean equals(String type) {
		return this == PresenceType.parse(type);
	}

	public Boolean in(PresenceType... types) {
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
			return null;
		}
	}

	public static boolean contains(String type) {
		return parse(type) != null;
	}
}