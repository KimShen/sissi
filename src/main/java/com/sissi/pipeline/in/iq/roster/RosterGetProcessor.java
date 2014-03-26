package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.roster.RelationRoster;
import com.sissi.ucenter.user.VCardContext;

/**
 * @author kim 2013-10-31
 */
public class RosterGetProcessor extends ProxyProcessor {

	private final VCardContext vcardContext;

	public RosterGetProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(this.prepare(context, protocol.cast(Roster.class).clear()).parent().reply().setType(ProtocolType.RESULT));
		return false;
	}

	private Roster prepare(JIDContext context, Roster roster) {
		for (Relation each : super.myRelations(context.jid())) {
			JID to = super.build(each.jid());
			roster.add(new GroupItem(each.cast(RelationRoster.class)).nickname(this.vcardContext.get(to, VCardContext.FIELD_NICKNAME).getValue(), to.user()));
		}
		return roster;
	}
}
