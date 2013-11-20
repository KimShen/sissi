package com.sissi.relation;

import java.util.Map;

<<<<<<< HEAD

=======
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
/**
 * @author kim 2013-11-13
 */
public interface Relation {

	public String getJID();

	public String getName();

	public String getSubscription();
<<<<<<< HEAD
	
	public Map<String, Object> plus();
=======

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
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
}
