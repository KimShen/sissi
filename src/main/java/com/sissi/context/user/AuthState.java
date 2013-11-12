package com.sissi.context.user;

/**
 * @author kim 2013-11-8
 */
public enum AuthState {

	ACCESS, REFUSE;

	public static AuthState valueOf(Boolean canAccess) {
		return canAccess ? ACCESS : REFUSE;
	}
}