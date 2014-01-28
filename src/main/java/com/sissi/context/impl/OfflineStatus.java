package com.sissi.context.impl;

import com.sissi.context.Status;
import com.sissi.context.StatusClauses;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月26日
 */
public class OfflineStatus implements Status {

	public final static OfflineStatus STATUS = new OfflineStatus();

	private final StatusClauses status;

	public OfflineStatus() {
		this.status = new EmptyClauses();
	}

	public OfflineStatus(StatusClauses status) {
		this.status = status;
	}

	public Status clear() {
		return this;
	}

	@Override
	public Status setClauses(StatusClauses clauses) {
		return this;
	}

	@Override
	public StatusClauses getClauses() {
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