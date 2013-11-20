package com.sissi.context.impl;

<<<<<<< HEAD
import org.apache.commons.lang.builder.ToStringBuilder;

=======
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2013-11-12
 */
public class UserBuilder implements JIDBuilder {
<<<<<<< HEAD
	
	private static final Integer DEFAULT_PRIORITY = 0;

	private String host;

	public UserBuilder(String host) {
		super();
		this.host = host;
	}
=======
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3

	@Override
	public JID build(String jid) {
		return new User(jid);
	}

<<<<<<< HEAD
	public JID build(String user, String resource) {
		return new User(user, this.host, resource);
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
=======
	@Override
	public JID build(String name, String pass) {
		return new User(name, pass);
	}

	@Override
	public JID build(String name, String pass, String resource) {
		return new User(name, pass, resource);
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	}
}
