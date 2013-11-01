package com.sisi.process.iq.fork;

import com.sisi.context.Context;
import com.sisi.context.JID;
import com.sisi.process.iq.Forker;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.Protocol.Type;
import com.sisi.protocol.core.Presence;
import com.sisi.protocol.iq.Item.Subscription;
import com.sisi.protocol.iq.Roster;
import com.sisi.relation.Relation;

/**
 * @author kim 2013-10-31
 */
public class GetRosterForker implements Forker {

	private final static String FORK_NAME = "query";

	private Relation relation;

	public GetRosterForker(Relation relation) {
		super();
		this.relation = relation;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		roster.clear();
		Presence presence = new Presence();
		for (JID jid : this.relation.relation(context.jid())) {
			roster.add(jid.asStringWithLoop(), jid.getUser(), Subscription.TO.toString(), null);
			presence.setFrom(jid.asStringWithLoop());
			presence.setTo(context.jid().asString());
			context.write(presence);
			presence.clear();
		}
		return roster;
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return Roster.class.isAssignableFrom(protocol.getClass());
	}

	public Type type() {
		return Type.GET;
	}

	@Override
	public String fork() {
		return FORK_NAME;
	}

}
