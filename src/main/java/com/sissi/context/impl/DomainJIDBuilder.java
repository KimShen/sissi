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

	private final None none = new None();

	private final StringBuffer emptyJID = new StringBuffer();

	private final String domain;

	private final Integer jid;

	public DomainJIDBuilder(String domain, Integer jid) {
		super();
		this.domain = domain;
		this.jid = jid;
	}

	@Override
	public JID build(String jid) {
		return (jid != null && !jid.isEmpty()) ? new User(jid) : this.none;
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

		public Boolean isValid() {
			return true;
		}

		public Boolean isValid(Boolean includeDomain) {
			return true;
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

		private Boolean valid = Boolean.TRUE;

		private String user;

		private String domain;

		private String resource;

		private User bareUser;

		private String asStringWithBare;

		private User() {

		}

		private User(String jid) {
			super();
			try {
				StringBuffer buffer = jid != null ? new StringBuffer(jid) : DomainJIDBuilder.this.emptyJID;
				int startHost = buffer.indexOf(DomainJIDBuilder.this.connectAt);
				this.user = startHost == -1 ? null : buffer.substring(0, startHost);
				int startResource = buffer.indexOf(DomainJIDBuilder.this.connectResource);
				this.domain = startResource == -1 ? buffer.substring(startHost != -1 ? startHost + 1 : 0) : buffer.substring(startHost + 1, startResource);
				this.resource = startResource == -1 ? null : buffer.substring(startResource + 1);
			} catch (Exception e) {
				this.valid = false;
			}
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

		public Boolean isValid() {
			return this.isValid(true);
		}

		public Boolean isValid(Boolean includeDomain) {
			return this.valid && this.selfReflectLength() && this.selfReflectChar(this.getUser(), true) && this.selfReflectChar(this.getDomain(), !includeDomain) && this.selfReflectChar(this.getResource(), true);
		}

		public String asString() {
			return this.asStringWithBare() + (this.resource != null ? DomainJIDBuilder.this.connectResource + this.resource : "");
		}

		public String asStringWithBare() {
			return this.asStringWithBare != null ? this.asStringWithBare : (this.domain != null ? this.stringWithBare(true) : this.stringWithBare(false));
		}

		private String stringWithBare(Boolean cached) {
			String asStringWithBare = (this.user != null ? this.user + DomainJIDBuilder.this.connectAt : "") + this.domain;
			return cached ? (this.asStringWithBare = asStringWithBare) : asStringWithBare;
		}

		private Boolean selfReflectLength() {
			return this.asString().length() < DomainJIDBuilder.this.jid;
		}

		private Boolean selfReflectChar(String part, Boolean allowNull) {
			return (part != null && !part.isEmpty()) ? !part.contains(DomainJIDBuilder.this.connectAt) && !part.contains(DomainJIDBuilder.this.connectResource) : (false || allowNull);
		}
	}
}
