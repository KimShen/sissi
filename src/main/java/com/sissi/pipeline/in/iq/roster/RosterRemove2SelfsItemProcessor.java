package com.sissi.pipeline.in.iq.roster;

import com.sissi.protocol.iq.roster.Item;

/**
 * @author kim 2013-11-18
 */
public class RosterRemove2SelfsItemProcessor extends Roster2SelfsItemProcessor {

	@Override
	protected String build() {
		return Item.Action.REMOVE.toString();
	}
}
