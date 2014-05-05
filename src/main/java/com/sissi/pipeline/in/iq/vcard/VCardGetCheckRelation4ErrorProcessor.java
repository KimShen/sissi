package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;

/**
 * 返回错误
 * 
 * @author kim 2014年1月26日
 */
public class VCardGetCheckRelation4ErrorProcessor extends VCardGetCheckRelationProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	public VCardGetCheckRelation4ErrorProcessor(boolean shortcut) {
		super(shortcut);
	}

	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
