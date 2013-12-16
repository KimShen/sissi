package com.sissi.pipeline.in.iq.roster;

import com.sissi.pipeline.in.iq.IQActionMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.GroupItem.Action;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月3日
 */
public class RosterActionMatcher extends IQActionMatcher {

	private final Action detail;

	public RosterActionMatcher(String type, String detail) {
		super(Roster.class, type);
		this.detail = Action.parse(detail);
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.matchAction(Roster.class.cast(protocol));
	}

	private boolean matchAction(Roster roster) {
		return roster.getFirstItem().getAction() == this.detail;
	}
}