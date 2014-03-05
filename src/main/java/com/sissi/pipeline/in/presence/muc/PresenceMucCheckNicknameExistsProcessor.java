package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.impl.OfflineJID;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucCheckNicknameExistsProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(Conflict.DETAIL_ELEMENT);

	private final RelationMucMapping relationMucMapping;

	public PresenceMucCheckNicknameExistsProcessor(RelationMucMapping relationMucMapping) {
		super();
		this.relationMucMapping = relationMucMapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID exists = this.relationMucMapping.mapping(super.build(protocol.getTo()));
		return OfflineJID.OFFLINE.equals(exists) || exists.same(context.jid()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
