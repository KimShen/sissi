package com.sissi.context.impl;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2013-11-12
 */
public class UserBuilder implements JIDBuilder {

	private final static Integer DEFAULT_PRIORITY = 0;

	private final None NONE = new None();

	private String host;

	public UserBuilder(String host) {
		super();
		this.host = host;
	}

	@Override
	public JID build(String jid) {
		return jid != null ? new User(jid) : NONE;
	}

	public JID build(String user, String resource) {
		return new User(user, this.host, resource);
	}

	private class None implements JID {

		private None() {

		}

		@Override
		public String getUser() {
			return "N/A";
		}

		@Override
		public String getHost() {
			return UserBuilder.this.host;
		}

		@Override
		public String getResource() {
			return null;
		}

		@Override
		public Integer getPriority() {
			return -1;
		}

		@Override
		public JID setUser(String user) {
			return this;
		}

		@Override
		public JID setHost(String host) {
			return this;
		}

		@Override
		public JID setResource(String resource) {
			return this;
		}

		@Override
		public JID setPriority(Integer priority) {
			return this;
		}

		@Override
		public JID getBare() {
			return this;
		}

		@Override
		public JID getBare(Boolean reuse) {
			return this;
		}

		@Override
		public Boolean isBare() {
			return true;
		}

		@Override
		public String asString() {
			return this.getUser() + "@" + this.getHost();
		}

		@Override
		public String asStringWithBare() {
			return this.asString();
		}
	}

	private class User implements JID {

		private Integer priority = DEFAULT_PRIORITY;

		private String user;

		private String host;

		private String resource;

		User(String jid) {
			super();
			int startHost = jid.indexOf("@");
			this.user = startHost == -1 ? null : jid.substring(0, startHost);
			int startResource = jid.indexOf("/");
			this.host = startResource == -1 ? jid.substring(startHost != -1 ? startHost + 1 : 0) : jid.substring(startHost + 1, startResource);
			this.resource = startResource == -1 ? null : jid.substring(startResource + 1);
		}

		User(String user, String host, String resource) {
			super();
			this.user = user;
			this.host = host;
			this.resource = resource;
		}

		public String getUser() {
			return this.user;
		}

		public User setUser(String user) {
			this.user = user;
			return this;
		}

		public String getHost() {
			return this.host;
		}

		public User setHost(String host) {
			this.host = host;
			return this;
		}

		public String getResource() {
			return this.resource;
		}

		public User setResource(String resource) {
			this.resource = resource;
			return this;
		}

		public Integer getPriority() {
			return priority;
		}

		public JID setPriority(Integer priority) {
			this.priority = priority;
			return this;
		}

		public JID getBare() {
			return UserBuilder.this.build(this.asString()).setResource(null);
		}

		public JID getBare(Boolean reuse) {
			return this.setResource(null);
		}

		@Override
		public Boolean isBare() {
			return this.resource == null;
		}

		public String asString() {
			return this.asStringWithBare() + (this.resource != null ? "/" + this.resource : "");
		}

		public String asStringWithBare() {
			return (this.user != null ? this.user + "@" : "") + this.host;
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
