package com.sisi.group.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.context.JID;
import com.sisi.group.Broadcast;
import com.sisi.group.Group;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.core.Message;

/**
 * @author kim 2013-10-27
 */
public class GroupBroadcast implements Broadcast, Group {

	private Log log = LogFactory.getLog(this.getClass());

	private Set<Context> contexts = new HashSet<Context>();

	private JID jid;

	public GroupBroadcast(JID jid) {
		super();
		this.jid = jid;
	}

	public JID jid() {
		return this.jid;
	}

	public void add(Context context) {
		this.contexts.add(context);
		this.log.info("JID: " + (context.jid() != null ? context.jid().asString() : "N/A") + "be joined into group " + this.jid.asString());
	}

	public void remove(Context context) {
		this.contexts.remove(context);
		this.log.info("JID: " + (context.jid() != null ? context.jid().asString() : "N/A") + "be removed from group " + this.jid.asString());
	}

	public void broadcast(String message, JID from, JID to) {
		this.broadcast(new Message(from, to, message));
	}

	public void broadcast(Protocol protocol) {
		for (Context context : contexts) {
			context.write(protocol);
		}
	}
}
