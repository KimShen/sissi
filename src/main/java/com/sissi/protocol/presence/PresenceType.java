package com.sissi.protocol.presence;

/**
 * @author kim 2014年1月16日
 */
public enum PresenceType {

	SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBE, UNSUBSCRIBED, UNAVAILABLE, AVAILABLE;

	public String toString() {
		if (AVAILABLE == this) {
			return null;
		}
		return super.toString().toLowerCase();
	}

	public Boolean equals(String type) {
		return this == PresenceType.parse(type);
	}

	public static PresenceType parse(String subscribe) {
		if (subscribe == null) {
			return AVAILABLE;
		}
		return PresenceType.valueOf(subscribe.toUpperCase());
	}
}