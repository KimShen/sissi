package com.sissi.pipeline.in.iq.roster;

import com.sissi.pipeline.in.iq.IQActionMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.GroupAction;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月3日
 */
public class RosterActionMatcher extends IQActionMatcher {

	private final GroupAction action;

	public RosterActionMatcher(String type, String action) {
		super(Roster.class, type);
		this.action = GroupAction.parse(action);
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && Roster.class.cast(protocol).getFirstItem().action(this.action);
	}
}