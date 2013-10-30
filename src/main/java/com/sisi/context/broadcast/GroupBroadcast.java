package com.sisi.context.broadcast;

import java.util.HashSet;
import java.util.Set;

import com.sisi.context.Broadcast;
import com.sisi.context.Context;
import com.sisi.context.Group;
import com.sisi.context.JID;
import com.sisi.protocol.core.Message;

/**
 * @author kim 2013-10-27
 */
public class GroupBroadcast implements Broadcast, Group {

	private Set<Context> contexts = new HashSet<Context>();

	private JID jid;

	public GroupBroadcast(JID jid) {
		super();
		this.jid = jid;
	}

	public void add(Context context) {
		this.contexts.add(context);
	}

	public void remove(Context context) {
		this.contexts.remove(context);
	}

	@Override
	public void broadcast(String message) {
		this.broadcast(new Message(message));
	}

	public void broadcast(Message message) {
		for (Context context : contexts) {
			message.setFrom(jid.asString());
			context.write(message);
		}
	}
}
