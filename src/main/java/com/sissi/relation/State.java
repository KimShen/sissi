package com.sissi.relation;

/**
 * @author kim 2013-11-8
 */
public enum State {

	SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBED;

	public String toString() {
		return super.toString().toLowerCase();
	}

	public static State parse(String state) {
		return State.valueOf(state.toUpperCase());
	}
}
