package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2014年1月21日
 */
public class RosterSetTrimNameProcessor implements Input {

	private final int length;

	public RosterSetTrimNameProcessor(int name) {
		super();
		this.length = name;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.cast(Roster.class).getFirstItem().trimName(this.length);
		return true;
	}
}