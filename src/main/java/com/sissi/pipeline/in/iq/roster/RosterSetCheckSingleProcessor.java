package com.sissi.pipeline.in.iq.roster;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2014年1月21日
 */
public class RosterSetCheckSingleProcessor extends ProxyProcessor {

	private final Integer items = 1;

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		List<GroupItem> items = Roster.class.cast(protocol).getItem();
		return items != null && items.size() == this.items ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(new ServerError().setType(ProtocolType.MODIFY).add(BadRequest.DETAIL)));
		return false;
	}
}
