package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;
import com.sissi.ucenter.muc.MucJIDs;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucCheckNicknameExistsProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("404").setType(ProtocolType.CANCEL).add(Conflict.DETAIL_ELEMENT);

	private final RelationMucMapping relationMucMapping;

	private final boolean multi;

	public PresenceMucCheckNicknameExistsProcessor(boolean multi, RelationMucMapping relationMucMapping) {
		super();
		this.relationMucMapping = relationMucMapping;
		this.multi = multi;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		MucJIDs exists = this.relationMucMapping.mapping(super.build(protocol.getTo()));
		return exists.isEmpty() || (this.multi ? exists.like(context.jid()) : exists.same(context.jid())) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
