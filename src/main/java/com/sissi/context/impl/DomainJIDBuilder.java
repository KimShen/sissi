package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2013年12月23日
 */
public class DomainJIDBuilder implements JIDBuilder {

	private final String name = "EMPTY";

	private final String connectAt = "@";

	private final String connectResource = "/";

	private final None none = new None();

	private final StringBuffer emptyJID = new StringBuffer();

	private final int lengthLimit;

	private final String domain;

	public DomainJIDBuilder(String domain, int jid) {
		super();
		this.domain = domain;
		this.lengthLimit = jid;
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
			this.toString = this.user() + DomainJIDBuilder.this.connectAt + this.domain();
		}

		@Override
		public String user() {
			return DomainJIDBuilder.this.name;
		}

		public boolean user(String jid) {
			return false;
		}

		@Override
		public String domain() {
			return DomainJIDBuilder.this.domain;
		}

		public JID domain(String domain) {
			return this;
		}

		@Override
		public String resource() {
			return null;
		}

		public JID resource(String resource) {
			return this;
		}

		@Override
		public JID bare() {
			return this;
		}

		public boolean valid() {
			return true;
		}

		public boolean valid(boolean excludeDomain) {
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

		private boolean parseValid = Boolean.TRUE;

		private User bareUser;

		private String user;

		private String domain;

		private String resource;

		private String asStringWithBare;

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
				this.parseValid = false;
			}
		}

		private User(String user, String resource) {
			super();
			this.user = user;
			this.resource = resource;
		}

		private User copy2Bare() {
			this.bareUser = new User(this.user, null);
			this.bareUser.domain = this.domain;
			return this.bareUser;
		}

		public String user() {
			return this.user;
		}

		public boolean user(String jid) {
			JID user = DomainJIDBuilder.this.build(jid);
			return this.asStringWithBare().equals(user.asStringWithBare());
		}

		public String domain() {
			return this.domain;
		}

		public JID domain(String domain) {
			this.domain = domain;
			return this;
		}

		public String resource() {
			return this.resource;
		}

		@Override
		public JID resource(String resource) {
			this.resource = resource;
			return this;
		}

		public JID bare() {
			return this.bareUser != null ? this.bareUser : this.copy2Bare();
		}

		public boolean valid() {
			return this.valid(true);
		}

		public boolean valid(boolean excludeDomain) {
			return this.parseValid && this.validLength() && this.validKeyword(this.user()) && this.validKeyword(this.domain(), excludeDomain) && this.validKeyword(this.resource());
		}

		public String asString() {
			return this.asStringWithBare() + (this.resource != null ? DomainJIDBuilder.this.connectResource + this.resource : "");
		}

		public String asStringWithBare() {
			return this.asStringWithBare != null ? this.asStringWithBare : (this.domain != null ? this.stringWithBare(true) : this.stringWithBare(false));
		}

		private String stringWithBare(boolean cached) {
			String asStringWithBare = (this.user != null ? this.user + DomainJIDBuilder.this.connectAt : "") + this.domain;
			return cached ? (this.asStringWithBare = asStringWithBare) : asStringWithBare;
		}

		private boolean validLength() {
			return this.asString().length() < DomainJIDBuilder.this.lengthLimit;
		}

		private boolean validKeyword(String part) {
			return this.validKeyword(part, true);
		}

		private boolean validKeyword(String part, boolean allowNull) {
			return (part != null && !part.isEmpty()) ? !part.contains(DomainJIDBuilder.this.connectAt) && !part.contains(DomainJIDBuilder.this.connectResource) : (false || allowNull);
		}
	}
}
