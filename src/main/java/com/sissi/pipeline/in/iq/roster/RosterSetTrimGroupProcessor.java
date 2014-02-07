package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2014年1月21日
 */
public class RosterSetTrimGroupProcessor implements Input {

	private final int group;

	public RosterSetTrimGroupProcessor(int group) {
		super();
		this.group = group;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Roster.class.cast(protocol).getFirstItem().trimGroup(this.group);
		return true;
	}
}