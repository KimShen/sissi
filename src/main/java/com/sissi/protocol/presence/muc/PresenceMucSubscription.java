package com.sissi.protocol.presence.muc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2014年2月11日
 */
public enum PresenceMucSubscription {

	REMOVE, TO, FROM, BOTH, NONE;

	private static Map<Integer, PresenceMucSubscription> MAPPING = new HashMap<Integer, PresenceMucSubscription>();

	static {
		MAPPING.put(-1, REMOVE);
		MAPPING.put(0, NONE);
		MAPPING.put(1, TO);
		MAPPING.put(2, FROM);
		MAPPING.put(3, BOTH);
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String subscribe) {
		return this == PresenceMucSubscription.parse(subscribe);
	}

	public boolean in(PresenceMucSubscription... subscriptions) {
		for (PresenceMucSubscription subscription : subscriptions) {
			if (this == subscription) {
				return true;
			}
		}
		return false;
	}

	public boolean in(String... subscriptions) {
		for (String subscription : subscriptions) {
			if (this == PresenceMucSubscription.parse(subscription)) {
				return true;
			}
		}
		return false;
	}

	public static PresenceMucSubscription parse(String subscribe) {
		return (subscribe == null || subscribe.toUpperCase().equals(REMOVE.name())) ? NONE : PresenceMucSubscription.valueOf(subscribe.toUpperCase());
	}

	public static String toString(Integer num) {
		PresenceMucSubscription sub = MAPPING.get(num);
		return sub != null ? sub.toString() : NONE.toString();
	}
}
