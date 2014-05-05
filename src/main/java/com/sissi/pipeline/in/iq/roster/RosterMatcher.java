package com.sissi.pipeline.in.iq.roster;

import com.sissi.pipeline.in.iq.IQActionMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.GroupAction;
import com.sissi.protocol.iq.roster.Roster;

/**
 * <iq to='juliet@example.com/balcony' type='result' id='roster_1'><query xmlns='jabber:iq:roster'><item jid='romeo@example.net' name='Romeo' subscription='指定类型'><group>Friends</group></item></query></iq>
 * 
 * @author kim 2013年12月3日
 */
public class RosterMatcher extends IQActionMatcher {

	private final GroupAction action;

	public RosterMatcher(String type, String action) {
		super(Roster.class, type);
		this.action = GroupAction.parse(action);
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Roster.class).first().action(this.action);
	}
}