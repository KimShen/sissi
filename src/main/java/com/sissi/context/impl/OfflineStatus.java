package com.sissi.context.impl;

import com.sissi.context.Status;
import com.sissi.context.StatusClauses;
import com.sissi.protocol.presence.PresenceType;

/**
 * 离线出席状态
 * 
 * @author kim 2014年1月26日
 */
class OfflineStatus implements Status {

	/**
	 * 全局离线出席状态
	 */
	public final static OfflineStatus STATUS = new OfflineStatus();

	private final StatusClauses status;

	private OfflineStatus() {
		this.status = new EmptyClauses();
	}

	/**
	 * 指定JID离线出席状态
	 * 
	 * @param status
	 */
	OfflineStatus(StatusClauses status) {
		this.status = status;
	}

	public Status clear() {
		return this;
	}

	@Override
	public Status clauses(StatusClauses clauses) {
		return this;
	}

	@Override
	public StatusClauses clauses() {
		return this.status;
	}

	private class EmptyClauses implements StatusClauses {

		private EmptyClauses() {

		}

		@Override
		public String find(String key) {
			switch (key) {
			case StatusClauses.KEY_TYPE:
				return PresenceType.UNAVAILABLE.toString();
			}
			return null;
		}
	}
}