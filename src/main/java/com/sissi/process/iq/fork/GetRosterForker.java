package com.sissi.process.iq.fork;

import com.sissi.context.Context;
import com.sissi.process.iq.Forker;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.core.Presence;
import com.sissi.protocol.iq.Item;
import com.sissi.protocol.iq.Roster;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationContext;

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
