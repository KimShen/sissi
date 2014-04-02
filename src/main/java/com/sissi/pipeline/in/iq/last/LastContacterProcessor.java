package com.sissi.pipeline.in.iq.last;

import com.sissi.context.JIDContext;
import com.sissi.context.StatusClauses;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.last.Last;

/**
 * @author kim 2014年2月9日
 */
public class LastContacterProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JIDContext to = super.findOne(protocol.parent().to() ? super.build(protocol.parent().getTo()) : context.jid(), true);
		context.write(protocol.cast(Last.class).seconds().seconds(to.idle()).setText(to.status().clauses().find(StatusClauses.KEY_STATUS)).parent().reply().setType(ProtocolType.RESULT));
		return false;
	}
}
