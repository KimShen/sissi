package com.sissi.protocol.iq.roster;

/**
 * @author kim 2014年1月16日
 */
public enum GroupAction {

	ADD, REMOVE;

	public static GroupAction parse(String action) {
		return action == null ? ADD : GroupAction.valueOf(action.toUpperCase());
	}

	public String toString() {
		return super.toString().toLowerCase();
	}
}