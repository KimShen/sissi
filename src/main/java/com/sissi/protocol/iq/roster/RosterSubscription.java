package com.sissi.protocol.iq.roster;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2014年1月16日
 */
public enum RosterSubscription {

	REMOVE, TO, FROM, BOTH, NONE;

	private static Map<Integer, RosterSubscription> MAPPING = new HashMap<Integer, RosterSubscription>();

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

	public Boolean equals(String subscribe) {
		return this == RosterSubscription.parse(subscribe);
	}

	public Boolean in(RosterSubscription... subscriptions) {
		for (RosterSubscription subscription : subscriptions) {
			if (this == subscription) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean in(String... subscriptions) {
		for (String subscription : subscriptions) {
			if (this == RosterSubscription.parse(subscription)) {
				return true;
			}
		}
		return false;
	}

	public static RosterSubscription parse(String subscribe) {
		return (subscribe == null || subscribe.toUpperCase().equals(REMOVE.name())) ? NONE : RosterSubscription.valueOf(subscribe.toUpperCase());
	}

	public static String toString(Integer num) {
		RosterSubscription sub = MAPPING.get(num);
		return sub != null ? sub.toString() : NONE.toString();
	}
}