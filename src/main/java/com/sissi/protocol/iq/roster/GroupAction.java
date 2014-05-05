package com.sissi.protocol.iq.roster;

/**
 * 是否为更新操作(或删除操作)
 * 
 * @author kim 2014年1月16日
 */
public enum GroupAction {

	UPDATE, REMOVE;

	public static GroupAction parse(String action) {
		return action == null ? UPDATE : RosterSubscription.REMOVE.equals(action) ? REMOVE : UPDATE;
	}

	public String toString() {
		return super.toString().toLowerCase();
	}
}