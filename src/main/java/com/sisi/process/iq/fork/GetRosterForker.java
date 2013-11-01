package com.sisi.process.iq.fork;

import com.sisi.context.Context;
import com.sisi.process.iq.Forker;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.Protocol.Type;
import com.sisi.protocol.core.Presence;
import com.sisi.protocol.iq.Item;
import com.sisi.protocol.iq.Roster;
import com.sisi.relation.Relation;
import com.sisi.relation.RelationContext;

/**
 * @author kim 2013-10-31
 */
public class GetRosterForker implements Forker {

	private final static String FORK_NAME = "query";

	private RelationContext relationContext;

	public GetRosterForker(RelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		roster.clear();
		Presence presence = new Presence();
		for (Relation relation : this.relationContext.relation(context.jid())) {
			roster.add(new Item(relation));
			presence.setFrom(relation.to().asStringWithLoop());
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
