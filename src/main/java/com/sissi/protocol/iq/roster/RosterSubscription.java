package com.sissi.protocol.iq.roster;

/**
 * @author kim 2014年1月16日
 */
public enum RosterSubscription {

	TO, BOTH, NONE;

	private final static String REMOVE = "remove";

	public String toString() {
		return this != NONE ? super.toString().toLowerCase() : "none";
	}

	public Boolean equals(String subscribe) {
		return this == RosterSubscription.parse(subscribe);
	}

	public static RosterSubscription parse(String subscribe) {
		if (subscribe == null || subscribe.toLowerCase() == REMOVE) {
			return NONE;
		}
		return RosterSubscription.valueOf(subscribe.toUpperCase());
	}
}