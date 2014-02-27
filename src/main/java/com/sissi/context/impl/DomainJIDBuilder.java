package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2013年12月23日
 */
public class DomainJIDBuilder implements JIDBuilder {

	private final String connectAt = "@";

	private final String connectResource = "/";

	private final int lengthLimit;

	private final String group;

	public DomainJIDBuilder(int lengthLimit, String group) {
		super();
		this.lengthLimit = lengthLimit;
		this.group = group;
	}

	@Override
	public JID build(String jid) {
		String trim = (jid != null ? jid.trim() : null);
		return trim != null && !trim.isEmpty() ? new User(trim) : OfflineJID.OFFLINE;
	}

	public JID build(String username, String resource) {
		return new User(username, resource);
	}

	private class User implements JID {

		private boolean valid = Boolean.TRUE;

		private String user;

		private String domain;

		private String resource;

		private String asStringWithBare;

		private User(String jid) {
			super();
			try {
				StringBuffer buffer = new StringBuffer(jid);
				int startHost = buffer.indexOf(DomainJIDBuilder.this.connectAt);
				this.user = startHost == -1 ? null : buffer.substring(0, startHost).trim();
				int startResource = buffer.indexOf(DomainJIDBuilder.this.connectResource);
				this.domain = (startResource == -1 ? buffer.substring(startHost != -1 ? startHost + 1 : 0) : buffer.substring(startHost + 1, startResource)).trim();
				this.resource = startResource == -1 ? null : buffer.substring(startResource + 1).trim();
			} catch (Exception e) {
				this.valid = Boolean.FALSE;
			}
		}

		private User(String user, String resource) {
			super();
			this.user = user;
			this.resource = resource;
		}

		private User copy2Bare() {
			User user = new User(this.user, null);
			user.domain(this.domain());
			return user;
		}

		public String user() {
			return this.user;
		}

		public String domain() {
			return this.domain;
		}

		public JID domain(String domain) {
			this.domain = domain;
			return this;
		}

		public String resource() {
			return this.resource != null && !this.resource.isEmpty() ? this.resource : null;
		}

		@Override
		public JID resource(String resource) {
			this.resource = resource;
			return this;
		}

		public JID bare() {
			return this.copy2Bare();
		}

		public JID clone() {
			return this.copy2Bare().resource(this.resource());
		}

		public boolean isBare() {
			return this.resource() == null || this.resource().isEmpty();
		}

		public boolean isGroup() {
			return DomainJIDBuilder.this.group.equals(this.domain);
		}

		public boolean same(JID jid) {
			return this.same(jid.asString());
		}

		public boolean same(String jid) {
			return jid != null && jid.equals(this.asString());
		}

		public boolean like(JID jid) {
			return this.like(jid.asStringWithBare());
		}

		public boolean like(String jid) {
			return jid != null && jid.equals(this.asStringWithBare());
		}

		public boolean valid() {
			return this.valid(true);
		}

		public boolean valid(boolean excludeDomain) {
			return this.valid && this.validLength() && this.validKeyword(this.user()) && this.validKeyword(this.domain(), excludeDomain);
		}

		public String asString() {
			return this.asStringWithBare() + (this.isBare() ? "" : DomainJIDBuilder.this.connectResource + this.resource());
		}

		public String asStringWithBare() {
			return this.asStringWithBare != null ? this.asStringWithBare : (this.domain != null ? this.stringWithBare(true) : this.stringWithBare(false));
		}

		public String asString(boolean bare) {
			return bare ? this.asStringWithBare() : this.asString();
		}

		private String stringWithBare(boolean cached) {
			String asStringWithBare = (this.user() != null ? this.user() + DomainJIDBuilder.this.connectAt : "") + this.domain();
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
