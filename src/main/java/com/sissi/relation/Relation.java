package com.sissi.relation;

import java.util.Map;

/**
 * @author kim 2013-11-13
 */
public interface Relation {

	public String getJID();

	public String getName();

	public String getSubscription();

	public Map<String, Object> toEntity();

	public enum State {
		
		SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBED;

		public String toString() {
			return super.toString().toLowerCase();
		}

		public static State parse(String state) {
			return State.valueOf(state.toUpperCase());
		}
	}
}
