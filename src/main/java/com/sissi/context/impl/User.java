package com.sissi.context.impl;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sissi.context.JID;

/**
 * @author kim 2013-10-30
 */
public class User implements JID {

	private String user;

	private String host;

	private String resource;

	public User(String user, String host) {
		this(user, host, null);
	}

	public User(String user, String host, String resource) {
		super();
		this.user = user;
		this.host = host;
		this.resource = resource;
	}

	public User(String jid) {
		super();
		int startHost = jid.indexOf("@");
		this.user = startHost == -1 ? null : jid.substring(0, startHost);
		int startResource = jid.indexOf("/");
		this.host = startResource == -1 ? jid.substring(startHost != -1 ? startHost + 1 : 0) : jid.substring(startHost + 1, startResource);
		this.resource = startResource == -1 ? null : jid.substring(startResource + 1);
	}

	public String user() {
		return this.user;
	}

	public String user(String user) {
		this.user = user;
		return this.user;
	}

	public String host() {
		return this.host;
	}

	public String host(String host) {
		this.host = host;
		return host;
	}

	public String resource() {
		return this.resource;
	}

	public String resource(String resource) {
		this.resource = resource;
		return this.resource;
	}

	@Override
	public Boolean bare() {
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
