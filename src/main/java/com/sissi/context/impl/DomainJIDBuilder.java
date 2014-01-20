package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2013年12月23日
 */
public class DomainJIDBuilder implements JIDBuilder {

	private final String noneName = "_NA";

	private final String connectAt = "@";

	private final String connectResource = "/";

	private final None noneUser = new None();

	private final String domain;

	public DomainJIDBuilder(String domain) {
		super();
		this.domain = domain;
	}

	@Override
	public JID build(String jid) {
		return (jid != null && !jid.isEmpty()) ? new User(jid) : this.noneUser;
	}

	public JID build(String username, String resource) {
		return new User(username, resource);
	}

	private class None implements JID {

		private final String toString;

		public None() {
			super();
			this.toString = this.getUser() + DomainJIDBuilder.this.connectAt + this.getDomain();
		}

		@Override
		public String getUser() {
			return DomainJIDBuilder.this.noneName;
		}

		@Override
		public String getDomain() {
			return DomainJIDBuilder.this.domain;
		}

		public JID setDomain(String domain) {
			return this;
		}

		@Override
		public String getResource() {
			return null;
		}

		public JID setResource(String resource) {
			return this;
		}

		@Override
		public JID getBare() {
			return this;
		}

		@Override
		public String asString() {
			return this.toString;
		}

		@Override
		public String asStringWithBare() {
			return this.asString();
		}
	}

	private class User implements JID {

		private String user;

		private String domain;

		private String resource;

		private User bareUser;

		private User() {

		}

		private User(String jid) {
			super();
			StringBuffer buffer = new StringBuffer(jid);
			int startHost = buffer.indexOf(DomainJIDBuilder.this.connectAt);
			this.user = startHost == -1 ? null : buffer.substring(0, startHost);
			int startResource = buffer.indexOf(DomainJIDBuilder.this.connectResource);
			this.domain = startResource == -1 ? buffer.substring(startHost != -1 ? startHost + 1 : 0) : buffer.substring(startHost + 1, startResource);
			this.resource = startResource == -1 ? null : buffer.substring(startResource + 1);
		}

		private User(String user, String resource) {
			super();
			this.user = user;
			this.resource = resource;
		}

		private User copy2NoneResourceClone() {
			this.bareUser = new User();
			this.bareUser.user = this.user;
			this.bareUser.domain = this.domain;
			return this.bareUser;
		}

		public String getUser() {
			return this.user;
		}

		public String getDomain() {
			return this.domain;
		}

		public JID setDomain(String domain) {
			this.domain = domain;
			return this;
		}

		public String getResource() {
			return this.resource;
		}

		@Override
		public JID setResource(String resource) {
			this.resource = resource;
			return this;
		}

		public JID getBare() {
			return this.bareUser != null ? this.bareUser : this.copy2NoneResourceClone();
		}

		public String asString() {
			return this.asStringWithBare() + (this.resource != null ? DomainJIDBuilder.this.connectResource + this.resource : "");
		}

		public String asStringWithBare() {
			return (this.user != null ? this.user + DomainJIDBuilder.this.connectAt : "") + this.domain;
		}
	}
}
