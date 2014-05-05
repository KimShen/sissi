package com.sissi.pipeline.in.iq.roster.set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * Item修正
 * 
 * @author kim 2014年1月21日
 */
public class RosterSetTrimItemProcessor implements Input {

	private final int items = 1;

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.cast(Roster.class).trimItem(this.items);
		return true;
	}
}
