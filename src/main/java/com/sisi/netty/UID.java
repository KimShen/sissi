package com.sisi.netty;

import com.sisi.context.JID;

/**
 * @author kim 2013-10-30
 */
public class UID implements JID {

	private String user;

	private String host;

	private String resource;

	public UID(String user, String host, String resource) {
		super();
		this.user = user;
		this.host = host;
		this.resource = resource;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String asString() {
		return this.user + "@" + this.host + (this.resource != null ? "/" + this.resource : "");
	}
}
