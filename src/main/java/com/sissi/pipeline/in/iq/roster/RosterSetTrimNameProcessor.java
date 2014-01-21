package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2014年1月21日
 */
public class RosterSetTrimNameProcessor implements Input {

	private final Integer name;

	public RosterSetTrimNameProcessor(Integer name) {
		super();
		this.name = name;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Roster.class.cast(protocol).getFirstItem().trimName(this.name);
		return true;
	}
}