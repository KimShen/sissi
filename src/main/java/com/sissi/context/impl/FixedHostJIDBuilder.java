package com.sissi.context.impl;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sissi.context.JID;
import com.sissi.context.JID.JIDBuilder;

/**
 * @author kim 2013-11-12
 */
public class FixedHostJIDBuilder implements JIDBuilder {

	private final static String NONE_NAME = "NONE";

	private final static String CONNECT_AT = "@";

	private final static String CONNECT_SLASH = "/";

	private final None NONE = new None();

	private final String host;

	public FixedHostJIDBuilder(String host) {
		super();
		this.host = host;
	}

	@Override
	public JID build(String jid) {
		return jid != null ? new User(jid) : NONE;
	}

	public JID build(String username, String resource) {
		return new User(username, resource);
	}

	private class None implements JID {

		@Override
		public String getUser() {
			return FixedHostJIDBuilder.NONE_NAME;
		}

		@Override
		public String getHost() {
			return FixedHostJIDBuilder.this.host;
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
			return this.getUser() + FixedHostJIDBuilder.CONNECT_AT + this.getHost();
		}

		@Override
		public String asStringWithBare() {
			return this.asString();
		}
	}

	private class User implements JID {

		private String user;

		private String host;

		private String resource;

		private User userWithoutResource;

		private User() {

		}

		private User(String jid) {
			super();
			int startHost = jid.indexOf(FixedHostJIDBuilder.CONNECT_AT);
			this.user = startHost == -1 ? null : jid.substring(0, startHost);
			int startResource = jid.indexOf(FixedHostJIDBuilder.CONNECT_SLASH);
			this.host = startResource == -1 ? jid.substring(startHost != -1 ? startHost + 1 : 0) : jid.substring(startHost + 1, startResource);
			this.resource = startResource == -1 ? null : jid.substring(startResource + 1);
			this.copy2NoneResourceClone();
		}

		private User(String user, String resource) {
			super();
			this.user = user;
			this.host = FixedHostJIDBuilder.this.host;
			this.resource = resource;
			this.copy2NoneResourceClone();
		}

		private void copy2NoneResourceClone() {
			this.userWithoutResource = new User();
			this.userWithoutResource.user = this.user;
			this.userWithoutResource.host = this.host;
		}

		public String getUser() {
			return this.user;
		}

		public String getHost() {
			return this.host;
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
			return this.userWithoutResource != null ? this.userWithoutResource : this;
		}

		public String asString() {
			return this.asStringWithBare() + (this.resource != null ? FixedHostJIDBuilder.CONNECT_SLASH + this.resource : "");
		}

		public String asStringWithBare() {
			return (this.user != null ? this.user + "@" : "") + this.host;
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
