package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2014年1月26日
 */
public class VCardGetCheckRelation4EmptyProcessor extends VCardGetCheckRelationProcessor {

	public VCardGetCheckRelation4EmptyProcessor(boolean free) {
		super(free);
	}

	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setType(ProtocolType.RESULT));
		return false;
	}
}
