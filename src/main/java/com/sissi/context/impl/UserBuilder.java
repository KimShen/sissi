package com.sissi.context.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2013-11-12
 */
public class UserBuilder implements JIDBuilder {

	@Override
	public JID build(String jid) {
		return new User(jid);
	}

	@Override
	public JID build(String name, String pass) {
		return new User(name, pass);
	}

	@Override
	public JID build(String name, String pass, String resource) {
		return new User(name, pass, resource);
	}
}
