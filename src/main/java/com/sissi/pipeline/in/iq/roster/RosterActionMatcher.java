package com.sissi.pipeline.in.iq.roster;

import com.sissi.pipeline.in.MatchClass;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.roster.Item.Action;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013-11-4
 */
public class RosterActionMatcher extends MatchClass {

	private final Type type;

	public RosterActionMatcher(String type) {
		super(Roster.class);
		this.type = Type.parse(type);
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.type.equals(protocol.getParent().getType());
	}

	public static class RosterActionDetailMatcher extends RosterActionMatcher {

		private final Action detail;

		public RosterActionDetailMatcher(String type, String detail) {
			super(type);
			this.detail = Action.parse(detail);
		}

		@Override
		public Boolean match(Protocol protocol) {
			return super.match(protocol) && this.matchAction(protocol);
		}

		private boolean matchAction(Protocol protocol) {
			return (Roster.class.cast(protocol)).getFirstItem().getAction() == this.detail;
		}
	}
}
