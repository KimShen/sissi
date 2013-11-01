package com.sisi.process.iq.fork;

import com.sisi.context.Context;
import com.sisi.context.user.User;
import com.sisi.process.iq.Forker;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.Protocol.Type;
import com.sisi.protocol.core.Presence;
import com.sisi.protocol.iq.Item;
import com.sisi.protocol.iq.Roster;
import com.sisi.relation.Relation;

/**
 * @author kim 2013-10-31
 */
public class SetRosterForker implements Forker {

	private final static String FORK_NAME = "query";

	private Relation relation;

	public SetRosterForker(Relation relation) {
		super();
		this.relation = relation;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		Presence presence = new Presence();
		for (Item item : roster.getItem()) {
			User user = new User(item.getJid());
			this.relation.relation(context.jid(), user);
			presence.setFrom(user.asString());
			presence.setTo(context.jid().asString());
			context.write(presence);
			presence.clear();
		}
		roster.clear();
		return roster;
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return Roster.class.isAssignableFrom(protocol.getClass());
	}

	public Type type() {
		return Type.SET;
	}

	@Override
	public String fork() {
		return FORK_NAME;
	}
}
