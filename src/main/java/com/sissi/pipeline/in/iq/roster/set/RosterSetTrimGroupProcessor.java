package com.sissi.pipeline.in.iq.roster.set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * Group修正
 * 
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
		protocol.cast(Roster.class).getFirstItem().trimGroup(this.group);
		return true;
	}
}