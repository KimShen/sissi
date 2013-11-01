package com.sisi.process.iq.fork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.process.iq.Forker;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.Protocol.Type;
import com.sisi.protocol.core.Presence;
import com.sisi.protocol.iq.Item;
import com.sisi.protocol.iq.Roster;
import com.sisi.relation.RelationContext;
import com.sisi.relation.impl.ItemRelation;

/**
 * @author kim 2013-10-31
 */
public class SetRosterForker implements Forker {

	private Log log = LogFactory.getLog(this.getClass());

	private final static String FORK_NAME = "query";

	private RelationContext relationContext;

	public SetRosterForker(RelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		Presence presence = new Presence();
		for (Item item : roster.getItem()) {
			ItemRelation relation = new ItemRelation(context.jid(), item);
			this.relationContext.relation(relation);
			this.log.info("Add relation: " + relation);
			presence.setFrom(relation.to().asStringWithLoop());
			presence.setTo(context.jid().asString());
			context.write(presence);
			this.log.debug("Presence: " + presence);
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
