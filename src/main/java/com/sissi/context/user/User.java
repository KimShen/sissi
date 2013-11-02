package com.sissi.context.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;

/**
 * @author kim 2013-10-30
 */
public class User implements JID {

	private final static Log LOG = LogFactory.getLog(User.class);

	private String user;

	private String host;

	private String resource;

	public User(String user, String host, String resource) {
		super();
		this.user = user;
		this.host = host;
		this.resource = resource;
	}

	public User(String jid) {
		super();
		LOG.debug("JID before parse: " + jid);
		int startHost = jid.indexOf("@");
		this.user = startHost == -1 ? null : jid.substring(0, startHost);
		int startResource = jid.indexOf("/");
		this.host = startResource == -1 ? jid.substring(startHost != -1 ? startHost + 1 : 0) : jid.substring(startHost + 1, startResource - startHost);
		this.resource = startResource == -1 ? null : jid.substring(startResource);
		LOG.debug("JID after parse: " + this.asString());
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

	@Override
	public Boolean naked() {
		return this.resource == null;
	}

	public Boolean loop() {
		return this.user == null;
	}

	public String asString() {
		return this.asStringWithLoop() + (this.resource != null ? "/" + this.resource : "");
	}

	public String asStringWithLoop() {
		return (this.user != null ? this.user + "@" : "") + this.host;
	}
}
