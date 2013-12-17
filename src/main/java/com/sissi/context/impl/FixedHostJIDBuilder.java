package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JID.JIDBuilder;

/**
 * @author kim 2013-11-12
 */
public class FixedHostJIDBuilder implements JIDBuilder {

	private final static String NONE_NAME = "NONE";

	private final static String CONNECT_AT = "@";

	private final static String CONNECT_RESOURCE = "/";

	private final None none = new None();

	private final String host;

	public FixedHostJIDBuilder(String host) {
		super();
		this.host = host;
	}

	@Override
	public JID build(String jid) {
		return jid != null ? new User(jid) : none;
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

		private User bareUser;

		private User() {

		}

		private User(String jid) {
			super();
			int startHost = jid.indexOf(FixedHostJIDBuilder.CONNECT_AT);
			this.user = startHost == -1 ? null : jid.substring(0, startHost);
			int startResource = jid.indexOf(FixedHostJIDBuilder.CONNECT_RESOURCE);
			this.host = startResource == -1 ? jid.substring(startHost != -1 ? startHost + 1 : 0) : jid.substring(startHost + 1, startResource);
			this.resource = startResource == -1 ? null : jid.substring(startResource + 1);
		}

		private User(String user, String resource) {
			super();
			this.user = user;
			this.host = FixedHostJIDBuilder.this.host;
			this.resource = resource;
		}

		private User copy2NoneResourceClone() {
			this.bareUser = new User();
			this.bareUser.user = this.user;
			this.bareUser.host = this.host;
			return this.bareUser;
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
			return this.bareUser != null ? this.bareUser : this.copy2NoneResourceClone();
		}

		public String asString() {
			return this.asStringWithBare() + (this.resource != null ? FixedHostJIDBuilder.CONNECT_RESOURCE + this.resource : "");
		}

		public String asStringWithBare() {
			return (this.user != null ? this.user + FixedHostJIDBuilder.CONNECT_AT : "") + this.host;
		}
	}
}
