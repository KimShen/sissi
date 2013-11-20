package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013-11-17
 */
public class RosterRemoveProcessor extends UtilProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		this.remove(context, roster);
		return true;
	}

	private void remove(JIDContext context, Roster roster) {
		for (Item item : roster.getItem()) {
			this.removeEach(context, item);
		}
	}

	private void removeEach(JIDContext context, Item item) {
		super.relationContext.remove(context.getJid(), super.jidBuilder.build(item.getJid()));
	}
}
